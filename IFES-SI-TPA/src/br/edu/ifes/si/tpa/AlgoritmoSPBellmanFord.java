/**
 * ****************************************************************************
 *  Compilação:         javac AlgoritmoSPBellmanFord.java
 *  Execução:           java AlgoritmoSPBellmanFord dados.txt vo
 *  Dependências:       DigrafoPonderado.java Aresta.java
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
 *****************************************************************************
 */
package br.edu.ifes.si.tpa;

/**
 * Esta classe implementa o caminho mínimo utilizando o algoritmo de
 * Bellman-Ford. Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoSPBellmanFord {

    private double[] distanciaPara;      // distanciaPara[v] = distância do caminho mais curto do caminho vo->v
    private Aresta[] arestaPara;         // arestaPara[v] = última aresta no caminho mais curto no caminho vo->v
    private boolean cicloNegativo;

    /**
     * Calcula a árvore de caminho mais curto de vo para todos os outros
     * vértices no digrafo de arestas ponderadas
     *
     * @param G digrafo de arestas ponderadas (digrafo sem ciclos negativos)
     * @param vo o vértice de origem
     * @throws IllegalArgumentException vo menor que 0 ou maior que V-1
     */
    public AlgoritmoSPBellmanFord(DigrafoPonderado G, int vo) {
        distanciaPara = new double[G.V()];
        arestaPara = new Aresta[G.V()];

        //inicialização
        for (int v = 0; v < G.V(); v++) {
            distanciaPara[v] = Double.POSITIVE_INFINITY;
        }
        distanciaPara[vo] = 0.0;

        //relaxamento
        for (int i = 0; i < G.V(); i++) {
            for (int v = 0; v < G.V(); v++) {
                for (Aresta a : G.adj(v)) {
                    relaxa(a);
                }
            }
        }

        //checagem de ciclos negativos
        for (Aresta a : G.arestas()) {
            int v1 = a.getV1();
            int v2 = a.getV2();

            if (distanciaPara[v2] > distanciaPara[v1] + a.peso()) {
                System.out.println("Ciclo negativo");
                cicloNegativo = true;
                return;
            }
        }

    }

    /**
     * Relaxa vértice v e insere na fila as respectivas extremidades da aresta
     * relaxada se houver alteração
     */
    private void relaxa(Aresta a) {
        int v1 = a.getV1(), v2 = a.getV2();
        if (distanciaPara[v2] > distanciaPara[v1] + a.peso()) {
            distanciaPara[v2] = distanciaPara[v1] + a.peso();
            arestaPara[v2] = a;
        }
    }

    /**
     * Retorna o tamanho do menor caminho do vértice ordem para o vértice v
     *
     * @param v o vértice de destino
     * @return retorna o tamanho do menor caminho do vértice origem para o
     * vértice v, ou Double.POSITIVE_INFINITYse não existir caminho
     */
    public double distanciaPara(int v) {
        return distanciaPara[v];
    }

    /**
     * Retorna verdadeiro se existe um caminho do vértice origem para o vértice
     * v
     *
     * @param v o vértice destino
     * @return verdadeiro se existe um caminho do vértice origem para o vértice
     * v, ou falso, caso contrário
     */
    public boolean temCaminhoPara(int v) {
        return distanciaPara[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Retorna o menor caminho do vértice origem para o vértice v
     *
     * @param v o vértice destino
     * @return o menor caminho do vértice origem para o vértice v como um
     * iterable de arestas e null, se não existir caminho
     */
    public Iterable<Aresta> caminhoPara(int v) {
        if (!temCaminhoPara(v)) {
            return null;
        }
        Pilha<Aresta> path = new Pilha<Aresta>();
        for (Aresta a = arestaPara[v]; a != null; a = arestaPara[a.getV1()]) {
            path.empilha(a);
        }
        return path;
    }

    /**
     * Testa a classe AlgoritmoSPBellmanFord
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int vo = Integer.parseInt(args[1]);
        DigrafoPonderado G = new DigrafoPonderado(in);

        AlgoritmoSPBellmanFord sp = new AlgoritmoSPBellmanFord(G, vo);

        if (!sp.cicloNegativo) {
            for (int v = 0; v < G.V(); v++) {
                if (sp.temCaminhoPara(v)) {
                    System.out.printf("%d para %d (%5.2f)  ", vo, v, sp.distanciaPara(v));
                    for (Aresta a : sp.caminhoPara(v)) {
                        System.out.print(a + "   ");
                    }
                    System.out.println();
                } else {
                    System.out.printf("%d para %d           sem caminho\n", vo, v);
                }
            }
        }

    }

}
