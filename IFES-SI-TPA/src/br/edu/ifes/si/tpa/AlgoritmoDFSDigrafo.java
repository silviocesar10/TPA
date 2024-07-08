/*******************************************************************************
 *  Compilação:        javac AlgoritmoDFSDigrafo.java
 *  Execução:          java AlgoritmoDFSDigrafo dados.txt vo
 *  Dependências:      Digrafo.java Pilha.java
 *  Arquivos de dados: Digrafo1.txt
 *  Link dos dados:    https://drive.google.com/open?id=0B3q56TwNCeXoc2tlbllCRmo1MTQ
 * 
 *  Determina acessibilidade em um dígrafo de um dado vértice utilizando busca em profundidade
 *
 *  % java AlgoritmoDFSDigrafo Digrafo1.txt 3
 *  3 to 0:  3-5-4-2-0
 *  3 to 1:  3-5-4-2-0-1
 *  3 to 2:  3-5-4-2
 *  3 to 3:  3
 *  3 to 4:  3-5-4
 *  3 to 5:  3-5
 *  3 to 6:  not connected
 *  3 to 7:  not connected
 *  3 to 8:  not connected
 *  3 to 9:  not connected
 *  3 to 10:  not connected
 *  3 to 11:  not connected
 *  3 to 12:  not connected
 * 
 * 3 para 0:  3-2-0
 * 3 para 1:  3-2-0-1
 * 3 para 2:  3-2
 * 3 para 3:  3
 * 3 para 4:  3-2-0-5-4
 * 3 para 5:  3-2-0-5
 * 3 para 6:  não conectado
 * 3 para 7:  não conectado
 * 3 para 8:  não conectado
 * 3 para 9:  não conectado
 * 3 para 10:  não conectado
 * 3 para 11:  não conectado
 * 3 para 12:  não conectado
 *
 ******************************************************************************/

package br.edu.ifes.si.tpa;

/**
 * Esta classe implementa o algoritmo de busca em profundidade em um dígrafo.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoDFSDigrafo {
    private boolean[] marcado;    // marcado[v1] = existe um caminho do vértice origem vo->v1?
    private int[] arestaPara;     // arestaPara[v1] = última aresta no menor caminho vértice origem vo->v1
    private final int vo;         // vo é o vértice de origem

    /**
     * Calcula um caminho dirigido de um vértice de origem vo para todos os outros vértices do dígrafo
     * @param G o dígrafo
     * @param vo o vértice de origem
     */
    public AlgoritmoDFSDigrafo(Digrafo G, int vo) {
        this.vo = vo;
        arestaPara = new int[G.V()];
        marcado = new boolean[G.V()];
        dfs(G, vo);
    }

    /**
     * Método algoritmoDFS para um vértice origem
     * @param G o dígrafo
     * @param vo o vértice origem
     */
    private void dfs(Digrafo G, int v) { 
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
     * Existe um caminho direcionado do vértice atual para o vértice v
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
     * Testa a classe AlgoritmoDFSDigrafo
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digrafo G = new Digrafo(in);

        int vo = Integer.parseInt(args[1]);
        AlgoritmoDFSDigrafo algoritmoDFS = new AlgoritmoDFSDigrafo(G, vo);

        for (int v = 0; v < G.V(); v++) {
            if (algoritmoDFS.temCaminhoPara(v)) {
                System.out.printf("%d para %d:  ", vo, v);
                for (int x : algoritmoDFS.caminhoPara(v)) {
                    if (x == vo) System.out.print(x);
                    else        System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d para %d:  não conectado\n", vo, v);
            }

        }
    }

}
