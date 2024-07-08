/*******************************************************************************
 *  Compilação:       javac AlgoritmoMSTKruskalGrafoPonderado.java
 *  Execução:         java  AlgoritmoMSTKruskalGrafoPonderado dados.txt
 *  Dependências:     GrafoPonderado.java Aresta.java Fila.java
 *                    UF.java In.java
 *  Arquivo de dados: GrafoPonderado1.txt
 *  Link dos dados:   https://drive.google.com/open?id=0B3q56TwNCeXoenFyMnlzX2ZyXzg
 *
 *  Calcula a árvore geradora mínima (MTS) utilizando o algoritmo de Kruskal.
 *
 *  %  java AlgoritmoMSTKruskalGrafoPonderado GrafoPonderado1.txt
 *  0-7 0.16000
 *  2-3 0.17000
 *  1-7 0.19000
 *  0-2 0.26000
 *  5-7 0.28000
 *  4-5 0.35000
 *  6-2 0.40000
 *  1.81000
 *
 ******************************************************************************/

package br.edu.ifes.si.tpa;

/**
 * Esta classe implementa a geração da árvore geradora mínima utilizando o algoritmo de Kruskal.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoMSTKruskalGrafoPonderado {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double peso;                            // peso da árvore geradora mínima (MST)
    private Fila<Aresta> mst = new Fila<Aresta>();  // arestas na árvore geradora mínima (MST)

    /**
     * Calcula a árvore geradora mínima do grafo ponderado.
     * @param G o grafo ponderado
     */
    public AlgoritmoMSTKruskalGrafoPonderado(GrafoPonderado G) {
        // estrutura mais eficiente para construir heap passando array de Arestas
        FilaPrioridadeMin<Aresta> pq = new FilaPrioridadeMin<Aresta>();
        for (Aresta e : G.arestas()) {
            pq.insere(e);
        }

        // executar o algoritmo guloso
        UF uf = new UF(G.V()); // instanciando uma estrutura do tipo union-find com o número de vértices do grafo
        while (!pq.isEmpty() && mst.tamanho() < G.V() - 1) {
            Aresta a = pq.delMin();
            int v1 = a.umVertice();
            int v2 = a.outroVertice(v1);
            if (!uf.conectado(v1, v2)) { // v1-v2 não cria ciclo
                uf.junta(v1, v2);  // juntar os componentes v1 a v2
                mst.enfileira(a);  // adiciona aresta na árvore geradora mínima (MST)
                peso += a.peso();
            }
        }

        // checa as condições de otimização
        assert checa(G);
    }

    /**
     * Retorna as arestas da árvore geradora mínima (MST).
     * @return as arestas da árvores geradora mínima como um iterable de arestas
     */
    public Iterable<Aresta> arestas() {
        return mst;
    }

    /**
     * Retorna a soma das arestas ponderadas na árvores geradora mínima (MST).
     * @return a soma das arestas ponderadas na árvores geradora mínima (MST)
     */
    public double peso() {
        return peso;
    }
    
    /**
     * Checa as condições de otimização 
     * @param G o grafo ponderado
     * @return verdadeiro se as condições forem satisfeitas, a falso, caso contrário
     */
    private boolean checa(GrafoPonderado G) {

        // checa peso total
        double total = 0.0;
        for (Aresta a : arestas()) {
            total += a.peso();
        }
        if (Math.abs(total - peso()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Peso das arestas não é igual peso(): %f vs. %f\n", total, peso());
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

        // checa que é uma árvore geradora mínima ( cortar condições de otimização )
        for (Aresta a : arestas()) {

            // todas arestas na árvores geradora mínima (MST) exceto 'a'
            uf = new UF(G.V());
            for (Aresta f : mst) {
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
     * Testa a classe AlgoritmoMSTKruskalGrafoPonderado
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        GrafoPonderado G = new GrafoPonderado(in);
        AlgoritmoMSTKruskalGrafoPonderado kruskal = new AlgoritmoMSTKruskalGrafoPonderado(G);
        for (Aresta a : kruskal.arestas()) {
            System.out.println(a);
        }
        System.out.printf("%.5f\n", kruskal.peso());
    }

}
