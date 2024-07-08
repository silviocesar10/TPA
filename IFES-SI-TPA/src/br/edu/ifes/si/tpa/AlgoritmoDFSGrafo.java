/******************************************************************************
 *  Compilação:        javac AlgoritmoDFSGrafo.java
 *  Execução:          java AlgoritmoDFSGrafo dados.txt 0
 *  Dependências:      Grafo.java Pilha.java
 *  Arquivos de dados: Grafo2.txt
 *  Link dos dados:    https://drive.google.com/open?id=0B3q56TwNCeXoMlQ1c1dGOXJRbG8
 *
 *  Executa pesquisa em profundiade em um grafo.
 *  % java AlgoritmoDFSGrafo Grafo2.txt 0
 *  0 para 0:  0
 *  0 para 1:  0-2-1
 *  0 para 2:  0-2
 *  0 para 3:  0-2-3
 *  0 para 4:  0-2-3-4
 *  0 para 5:  0-2-3-5
 *
 ******************************************************************************/

package br.edu.ifes.si.tpa;

/**
 * Esta classe implementa o algoritmo de busca em profundidade em um grafo.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoDFSGrafo {
    private boolean[] marcado;    // marcado[v1] = existe um caminho do vértice origem vo->v1?
    private int[] arestaPara;     // arestaPara[v1] = última aresta no menor caminho vértice origem vo->v1
    private final int vo;         // vo é o vértice de origem

    /**
     * Identifica o caminho entre <tt>vo</tt> e todos os outros vértices no grafo <tt>G</tt>.
     * @param G o grafo
     * @param vo o vértice de origem
     */
    public AlgoritmoDFSGrafo(Grafo G, int vo) {
        this.vo = vo;
        arestaPara = new int[G.V()];
        marcado = new boolean[G.V()];
        dfs(G, vo);
    }

    /**
     * Método algoritmoDFS para um vértice origem
     * @param G o grafo
     * @param v o vértice origem
     */
    private void dfs(Grafo G, int v) {
        marcado[v] = true;
        for (Aresta a : G.adj(v)) {
            int x = a.getV2();
            if (!marcado[x]) {
                arestaPara[x] = v;
                dfs(G, x);
            }
        }
    }

    /**
     * Existe um caminho do vértice atual para o vértice v
     * @param v the vertex
     * @return verdadeiro se existir um caminho direcionado, ou falso, caso contrário
     */
    public boolean temCaminhoPara(int v) {
        return marcado[v];
    }

    /**
     * Retorna um caminho do vértice origem para o vértice v ou null se não existir caminho
     * @param v o vértice
     * @return a sequência de vértices no menor caminho, como iterable
     */
    public Iterable<Integer> caminhoPara(int v) {
        if (!temCaminhoPara(v)) return null;
        Pilha<Integer> caminho = new Pilha<Integer>();
        for (int x = v; x != vo; x = arestaPara[x])
            caminho.empilha(x);
        caminho.empilha(vo);
        return caminho;
    }

    /**
     * Testa a classe AlgoritmoDFSGrafo
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Grafo G = new Grafo(in);
        int vo = Integer.parseInt(args[1]);
        AlgoritmoDFSGrafo algoritmoDFS = new AlgoritmoDFSGrafo(G, vo);

        for (int v = 0; v < G.V(); v++) {
            if (algoritmoDFS.temCaminhoPara(v)) {
                System.out.printf("%d para %d:  ", vo, v);
                for (int x : algoritmoDFS.caminhoPara(v)) {
                    if (x == vo) System.out.print(x);
                    else         System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d para %d:  não conectado\n", vo, v);
            }

        }
    }

}
