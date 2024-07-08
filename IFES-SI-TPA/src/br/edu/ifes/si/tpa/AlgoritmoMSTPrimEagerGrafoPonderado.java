/******************************************************************************
 *  Compilação:         javac AlgoritmoMSTPrimGrafoPonderado.java
 *  Execução:           java AlgoritmoMSTPrimGrafoPonderado dados.txt
 *  Dependências:       GrafoPonderado.java Aresta.java Fila.java
 *                      FilaPrioridadeMinIndex.java UF.java In.java
 *  Arquivo de dados:   GrafoPonderado1.txt
 *  Link dos dados:     https://drive.google.com/open?id=0B3q56TwNCeXoenFyMnlzX2ZyXzg
 *
 *  Calcula a árvore geradora mínima (MTS) utilizando o algoritmo de Prim.
 *
 *  %  java AlgoritmoMSTPrimGrafoPonderado GrafoPonderado1.txt
 *  7-1 0.19000
 *  0-2 0.26000
 *  2-3 0.17000
 *  5-4 0.35000
 *  7-5 0.28000
 *  2-6 0.40000
 *  0-7 0.16000
 *  1.81000
 ******************************************************************************/

package br.edu.ifes.si.tpa;

/**
 * Esta classe implementa a geração da árvore geradora mínima utilizando o algoritmo de Prim.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoMSTPrimEagerGrafoPonderado {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Aresta[] arestaPara;         // arestaPara[v] = aresta mais curta do vértice da árvore para vértice não-árvore
    private double[] distanciaPara;      // distanciaPara[v] = peso de tal aresta mais curta
    private boolean[] marcado;           // marcado[v] = verdadeiro se v está na árvore, falso, caso contrário
    private FilaPrioridadeMinIndex<Double> pq;

    /**
     * Calcula a árvore geradora mínima do grafo ponderado.
     * @param G o grafo ponderado
     */
    public AlgoritmoMSTPrimEagerGrafoPonderado(GrafoPonderado G) {
        arestaPara = new Aresta[G.V()];
        distanciaPara = new double[G.V()];
        marcado = new boolean[G.V()];
        pq = new FilaPrioridadeMinIndex<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distanciaPara[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // executa Prim à partir de cada vértice para obter a árvore geradora mínima
            if (!marcado[v]) prim(G, v);     

        // checa condições de otimização
        assert checa(G);
    }

    /**
     * Método para rodar o algoritmo prim no grafo G, iniciado do vértice de origem vo
     * @param G o grafo
     * @param vo o vértice origem
     */
    private void prim(GrafoPonderado G, int vo) {
        distanciaPara[vo] = 0.0;
        pq.insere(vo, distanciaPara[vo]);
        while (!pq.isEmpty()) {
            int v = pq.removeMin();
            visita(G, v);
        }
    }

    /**
     * Método para visitar um determinado vértice v
     * @param G o grafo
     * @param v o vértice
     */
    private void visita(GrafoPonderado G, int v) {
        marcado[v] = true;
        for (Aresta a : G.adj(v)) {
            int v2 = a.outroVertice(v);
            if (marcado[v2]) continue;         // v1-v2 é uma aresta obsoleta
            if (a.peso() < distanciaPara[v2]) {
                distanciaPara[v2] = a.peso();
                arestaPara[v2] = a;
                if (pq.contem(v2)) pq.diminuiChave(v2, distanciaPara[v2]);
                else               pq.insere(v2, distanciaPara[v2]);
            }
        }
    }

    /**
     * Retorna as arestas na árvore geradora mínima.
     * @return as arestas na árvore geradora mínima como iterable de arestas
     */
    public Iterable<Aresta> arestas() {
        Fila<Aresta> mst = new Fila<Aresta>();
        for (int v = 0; v < arestaPara.length; v++) {
            Aresta a = arestaPara[v];
            if (a != null) {
                mst.enfileira(a);
            }
        }
        return mst;
    }

    /**
     * Retorna a soma das arestas ponderadas na árvore geradora mínima.
     * @return a soma das arestas ponderadas na árvore geradora mínima.
     */
    public double peso() {
        double peso = 0.0;
        for (Aresta a : arestas())
            peso += a.peso();
        return peso;
    }


    /**
     * Checa as condições de otimização 
     * @param G o grafo ponderado
     * @return verdadeiro se as condições forem satisfeitas, e falso, caso contrário
     */
    private boolean checa(GrafoPonderado G) {

        // checa peso
        double pesoTotal = 0.0;
        for (Aresta a : arestas()) {
            pesoTotal += a.peso();
        }
        if (Math.abs(pesoTotal - peso()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Peso de arestas não é igual peso(): %f vs. %f\n", pesoTotal, peso());
            return false;
        }

        // checa que é acíclico
        UF uf = new UF(G.V());
        for (Aresta a : arestas()) {
            int v1 = a.umVertice(), v2 = a.outroVertice(v1);
            if (uf.conectado(v1, v2)) {
                System.err.println("Não é floresta");
                return false;
            }
            uf.junta(v1, v2);
        }

        // chega que é uma árvores geradora
        for (Aresta a : G.arestas()) {
            int v1 = a.umVertice(), v2 = a.outroVertice(v1);
            if (!uf.conectado(v1, v2)) {
                System.err.println("Não é uma árvore geradora mínima");
                return false;
            }
        }

        // checa que é uma árvore geradora mínima (cortar condições de otimização)
        for (Aresta a : arestas()) {

            // todas arestas na árvores geradora mínima (MST) exceto 'a'
            uf = new UF(G.V());
            for (Aresta f : arestas()) {
                int x = f.umVertice(), y = f.outroVertice(x);
                if (f != a) uf.junta(x, y);
            }

            // checa que é aresta de peso min em corte de cruzamento
            for (Aresta f : G.arestas()) {
                int x = f.umVertice(), y = f.outroVertice(x);
                if (!uf.conectado(x, y)) {
                    if (f.peso() < a.peso()) {
                        System.err.println("Aresta " + f + " viola as condições de corte de optimização");
                        return false;
                    }
                }
            }

        }

        return true;
    }

    /**
     * Testa a classe AlgoritmoMSTPrimEagerGrafoPonderado
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        GrafoPonderado G = new GrafoPonderado(in);
        AlgoritmoMSTPrimEagerGrafoPonderado prim = new AlgoritmoMSTPrimEagerGrafoPonderado(G);
        for (Aresta a : prim.arestas()) {
            System.out.println(a);
        }
        System.out.printf("%.5f\n", prim.peso());
    }


}
