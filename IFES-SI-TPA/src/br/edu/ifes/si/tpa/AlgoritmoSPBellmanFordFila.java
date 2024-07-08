/******************************************************************************
 *  Compilação:         javac AlgoritmoSPBellmanFord.java
 *  Execução:           java AlgoritmoSPBellmanFord dados.txt vo
 *  Dependências:       DigrafoPonderado.java Aresta.java AlgoritmoCicloDirecionado.java Fila.java
 *  Arquivo de dados:   DigrafoPonderado1.txt
 *  Link dos dados:
 *
 *  Algoritmo Bellman-Ford. Calcula a árvore que representa o menor caminho de um vértice de origem para todos os outros.
 *  Ou encontra um ciclo de custo negativo alcançável à partir do vértice de origem.
 *  Assume pesos positivos e negativos das arestas.
 *
 *  % java AlgoritmoSPBellmanFord DigrafoPonderado1.txt 0
 *  0 to 0 ( 0.00)  
 *  0 to 1 ( 0.93)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   4->5  0.35   5->1  0.32
 *  0 to 2 ( 0.26)  0->2  0.26   
 *  0 to 3 ( 0.99)  0->2  0.26   2->7  0.34   7->3  0.39   
 *  0 to 4 ( 0.26)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   
 *  0 to 5 ( 0.61)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   4->5  0.35
 *  0 to 6 ( 1.51)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   
 *  0 to 7 ( 0.60)  0->2  0.26   2->7  0.34   
 *
 *  % java AlgoritmoSPBellmanFord DigrafoPonderado3CicloNegativo.txt 0
 *  4->5  0.35
 *  5->4 -0.66
 *
 *
 ******************************************************************************/

package br.edu.ifes.si.tpa;

/**
 * Esta classe implementa o caminho mínimo utilizando o algoritmo de Bellman-Ford.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoSPBellmanFordFila {
    private double[] distanciaPara;      // distanciaPara[v] = distância do caminho mais curto do caminho vo->v
    private Aresta[] arestaPara;         // arestaPara[v] = última aresta no caminho mais curto no caminho vo->v
    private boolean[] naFila;            // naFila[v] = v existe na fila?
    private Fila<Integer> fila;          // fila de vértices para relaxar
    private int custo;                   // número de chamadas para relaxa()
    private Iterable<Aresta> ciclo;      // ciclo negativo (não null, se não existir)

    /**
     * Calcula a árvore de caminho mais curto de vo para todos os outros vértices no digrafo de arestas ponderadas
     * @param G digrafo de arestas ponderadas (digrafo sem ciclos negativos)
     * @param vo o vértice de origem
     * @throws IllegalArgumentException vo menor que 0 ou maior que V-1
     */
    public AlgoritmoSPBellmanFordFila(DigrafoPonderado G, int vo) {
        distanciaPara  = new double[G.V()];
        arestaPara  = new Aresta[G.V()];
        naFila = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distanciaPara[v] = Double.POSITIVE_INFINITY;
        distanciaPara[vo] = 0.0;

        // algoritmo Bellman-Ford
        fila = new Fila<Integer>();
        fila.enfileira(vo);
        naFila[vo] = true;
        while (!fila.isEmpty() && !temCicloNegativo()) {
            int v = fila.desenfileira();
            naFila[v] = false;
            relaxa(G, v);
        }

        assert checar(G, vo);
    }

    /**
     * Relaxa vértice v e insere na fila as respectivas extremidades da aresta relaxada se houver alteração
     */
    private void relaxa(DigrafoPonderado G, int v1) {
        for (Aresta a : G.adj(v1)) {
            int v2 = a.getV2();
            if (distanciaPara[v2] > distanciaPara[v1] + a.peso()) {
                distanciaPara[v2] = distanciaPara[v1] + a.peso();
                arestaPara[v2] = a;
                if (!naFila[v2]) {
                    fila.enfileira(v2);
                    naFila[v2] = true;
                }
            }
            
            if (custo++ % G.V() == 0) {
                encontraCicloNegativo();
                if (temCicloNegativo()) return;  // encontrou um ciclo negativo
            }
        }
    }

    /**
     * Existe um ciclo negativo alcançável à partir do vértice de origem vo?
     * @return true se existir um ciclo negativo alcançável à partir de vo, e falso, caso contrário
     */
    public boolean temCicloNegativo() {
        return ciclo != null;
    }

    /**
     * Retorna o ciclo negativo alcançável à partir do vértice de origem vo, ou null se não existir.
     * @return o ciclo negativo alcançável à partir do vértice de origem vo, ou null se não existir.
     */
    public Iterable<Aresta> cicloNegativo() {
        return ciclo;
    }

    
    // procurando um ciclo no grafo predecessor
    private void encontraCicloNegativo() {
        int V = arestaPara.length;
        DigrafoPonderado spt = new DigrafoPonderado(V);
        for (int v = 0; v < V; v++)
            if (arestaPara[v] != null)
                spt.addAresta(arestaPara[v]);

        AlgoritmoCicloDirecionado algoritmoCicloDirecionado = new AlgoritmoCicloDirecionado(spt);
        ciclo = algoritmoCicloDirecionado.ciclo();
    }

    /**
     * Retorna o tamanho do menor caminho do vértice ordem para o vértice v
     * @param v o vértice de destino
     * @return retorna o tamanho do menor caminho do vértice origem para o vértice v, ou Double.POSITIVE_INFINITYse não existir caminho
     */
    public double distanciaPara(int v) {
        if (temCicloNegativo())
            throw new UnsupportedOperationException("Existe ciclo negativo");
        return distanciaPara[v];
    }

    /**
     * Retorna verdadeiro se existe um caminho do vértice origem para o vértice v
     * @param v o vértice destino
     * @return verdadeiro se existe um caminho do vértice origem para o vértice v, ou falso, caso contrário
     */
    public boolean temCaminhoPara(int v) {
        return distanciaPara[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Retorna o menor caminho do vértice origem para o vértice v
     * @param v o vértice destino
     * @return o menor caminho do vértice origem para o vértice v como um iterable de arestas e null, se não existir caminho
     */
    public Iterable<Aresta> caminhoPara(int v) {
        if (temCicloNegativo())
            throw new UnsupportedOperationException("Existe ciclo negativo");
        if (!temCaminhoPara(v)) return null;
        Pilha<Aresta> path = new Pilha<Aresta>();
        for (Aresta a = arestaPara[v]; a != null; a = arestaPara[a.getV1()]) {
            path.empilha(a);
        }
        return path;
    }

    // checar optimality conditions: either 
    // (i) there exists a negative ciclo reacheable from vo
    //     or 
    // (ii)  for all edges a = v->v2:            distanciaPara[v2] <= distanciaPara[v] + a.peso()
    // (ii') for all edges a = v->v2 on the SPT: distanciaPara[v2] == distanciaPara[v] + a.peso()
    /**
     * Checar as condições de otimização
     * @param G o dígrafo ponderado
     * @param vo o vértice origem
     * @return verdadeiro caso sejam satisfeitas as condições
     */
    private boolean checar(DigrafoPonderado G, int vo) {

        // tem um ciclo negativo
        if (temCicloNegativo()) {
            double peso = 0.0;
            for (Aresta a : cicloNegativo()) {
                peso += a.peso();
            }
            if (peso >= 0.0) {
                System.err.println("erro: peso do ciclo negativo = " + peso);
                return false;
            }
        }

        // sem ciclo negativo alcançável à partir do vértice origem
        else {

            // checar se distanciaPara[v] e arestaPara[v] são consistentes
            if (distanciaPara[vo] != 0.0 || arestaPara[vo] != null) {
                System.err.println("distanciaPara[vo] e arestaPara[vo] inconsistentes");
                return false;
            }
            for (int v = 0; v < G.V(); v++) {
                if (v == vo) continue;
                if (arestaPara[v] == null && distanciaPara[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("distanciaPara[] e arestaPara[] inconsistentes");
                    return false;
                }
            }

            // checar se todas as arestas a = v->v2 satisfazem distanciaPara[v2] <= distanciaPara[v] + a.peso()
            for (int v = 0; v < G.V(); v++) {
                for (Aresta a : G.adj(v)) {
                    int w = a.getV2();
                    if (distanciaPara[v] + a.peso() < distanciaPara[w]) {
                        System.err.println("aresta " + a + " não relaxada");
                        return false;
                    }
                }
            }

            // checar se todas as arestas a = v->v2 no SPT satisfazem distanciaPara[v2] == distanciaPara[v] + a.peso()
            for (int w = 0; w < G.V(); w++) {
                if (arestaPara[w] == null) continue;
                Aresta a = arestaPara[w];
                int v = a.getV1();
                if (w != a.getV2()) return false;
                if (distanciaPara[v] + a.peso() != distanciaPara[w]) {
                    System.err.println("aresta " + a + " no caminho mais curto não justa");
                    return false;
                }
            }
        }

        System.out.println("Satisfeitas as condições de otimização");
        System.out.println();
        return true;
    }

    /**
     * Testa a classe AlgoritmoSPBellmanFordFila
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int vo = Integer.parseInt(args[1]);
        DigrafoPonderado G = new DigrafoPonderado(in);

        AlgoritmoSPBellmanFordFila sp = new AlgoritmoSPBellmanFordFila(G, vo);

        // imprimi o ciclo negativo
        if (sp.temCicloNegativo()) {
            for (Aresta a : sp.cicloNegativo())
                System.out.println(a);
        }

        // imprimi os caminhos mais curtos
        else {
            for (int v = 0; v < G.V(); v++) {
                if (sp.temCaminhoPara(v)) {
                    System.out.printf("%d para %d (%5.2f)  ", vo, v, sp.distanciaPara(v));
                    for (Aresta a : sp.caminhoPara(v)) {
                        System.out.print(a + "   ");
                    }
                    System.out.println();
                }
                else {
                    System.out.printf("%d para %d           sem caminho\n", vo, v);
                }
            }
        }

    }

}
