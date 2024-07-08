/*******************************************************************************
 *  Compilação:        javac AlgoritmoBFSGrafo.java
 *  Execução:          java AlgoritmoBFSGrafo dados.txt vo
 *  Dependências:      Grafo.java Fila.java Pilha.java
 *  Arquivos de dados: Grafo2.txt
 *  Link dos dados:    https://drive.google.com/open?id=0B3q56TwNCeXoMlQ1c1dGOXJRbG8
 *
 *  Executa pesquisa em largura em um grafo.
 *
 *  %  java AlgoritmoBFSGrafo Grafo2.txt 0
 *  0 para 0 (0):  0
 *  0 para 1 (1):  0-1
 *  0 para 2 (1):  0-2
 *  0 para 3 (2):  0-2-3
 *  0 para 4 (2):  0-2-4
 *  0 para 5 (1):  0-5
 *
 ******************************************************************************/

package br.edu.ifes.si.tpa;


/**
 * Esta classe implementa o algoritmo de busca em largura em um grafo.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoBFSGrafo {
    private boolean[] marcado;    // marcado[v1] = existe um caminho do vértice origem vo->v1?
    private int[] arestaPara;     // arestaPara[v1] = última aresta no menor caminho vértice origem vo->v1
    private int[] distanciaPara;  // distanciaPara[v1] = tamanho do menor caminho vértice origem vo->v1

    /**
     * Verifica o menor caminho de um vértice origem vo e todos os demais vértices do grafo
     * @param G o dígrafo
     * @param vo o vértice origem
     */
    public AlgoritmoBFSGrafo(Grafo G, int vo) {
        marcado = new boolean[G.V()];
        distanciaPara = new int[G.V()];
        arestaPara = new int[G.V()];
        bfs(G, vo);
    }

    /**
     * Método algoritmoBFS para um vértice origem
     * @param G o grafo
     * @param vo o vértice origem
     */
    private void bfs(Grafo G, int vo) {
        Fila<Integer> f = new Fila<Integer>();
        f.enfileira(vo);
        marcado[vo] = true;
        distanciaPara[vo] = 0;
        while (!f.isEmpty()) {
            int v = f.desenfileira();
            for (Aresta a : G.adj(v)) {
                int x = a.getV2();
                if (!marcado[x]) {
                    arestaPara[x] = v;
                    distanciaPara[x] = distanciaPara[v] + 1;
                    marcado[x] = true;
                    f.enfileira(x);
                }
            }
        }
    }

    /**
     * Existe um caminho direcionado do vértice atual para o vértice v
     * @param v o vértice
     * @return verdadeiro se existir um caminho direcionado, ou falso, caso contrário
     */
    public boolean temCaminhoPara(int v) {
        return marcado[v];
    }

    /**
     * Retorna o número de arestas no menor caminho do vértice origem v
     * @param v o vértice
     * @return o número de arestas no menor caminho
     */
    public int distanciaPara(int v) {
        return distanciaPara[v];
    }

    /**
     * Retorna um caminho do vértice origem para o vértice v ou null se não existir caminho
     * @param v o vértice
     * @return a sequência de vértices no menor caminho, como iterable
     */
    public Iterable<Integer> caminhoPara(int v) {
        if (!temCaminhoPara(v)) return null;
        Pilha<Integer> caminho = new Pilha<Integer>();
        int x;
        for (x = v; distanciaPara[x] != 0; x = arestaPara[x])
            caminho.empilha(x);
        caminho.empilha(x);
        return caminho;
    }


    /**
     * Testa a classe AlgoritmoBFSGrafo
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Grafo G = new Grafo(in);
        //System.out.println(G);

        int vo = Integer.parseInt(args[1]);
        AlgoritmoBFSGrafo algoritmoBFS = new AlgoritmoBFSGrafo(G, vo);

        for (int v = 0; v < G.V(); v++) {
            if (algoritmoBFS.temCaminhoPara(v)) {
                System.out.printf("%d para %d (%d):  ", vo, v, algoritmoBFS.distanciaPara(v));
                for (int x : algoritmoBFS.caminhoPara(v)) {
                    if (x == vo) System.out.print(x);
                    else         System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d to %d (-):  não conectado\n", vo, v);
            }

        }
    }


}
