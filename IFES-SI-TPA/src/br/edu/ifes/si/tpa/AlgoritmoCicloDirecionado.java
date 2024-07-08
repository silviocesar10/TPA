/******************************************************************************
 *  Compilação:  javac ArestaCiclo.java
 *  Execução:    java ArestaCiclo V E F
 *  Dependências: DigrafoPonderado.java Aresta.java Pilha.java
 *
 *  Encontra um ciclo direcionado em um digrafo com arestas ponderadas.
 *  Roda em tempo O(A + V).
 *
 ******************************************************************************/

package br.edu.ifes.si.tpa;

/**
 * Esta classe tem por objetivo verificar a existência de ciclo direcionado em um digrafo.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoCicloDirecionado {
    private boolean[] marcado;      // marcado[v] = o vértice v já foi marcado?
    private Aresta[] arestaPara;    // arestaPara[v] = última aresta no caminho para v
    private boolean[] naPilha;      // naPilha[v] = é um vértice na pilha?
    private Pilha<Aresta> ciclo;    // ciclo direcionado (ou null se não existir ciclo)

    /**
     * Determina se o digrafo com arestas ponderadas tem um ciclo direcionado e, se exitir, encontra o ciclo.
     * @param G o digrafo com arestas ponderadas
     */
    public AlgoritmoCicloDirecionado(DigrafoPonderado G) {
        marcado  = new boolean[G.V()];
        naPilha = new boolean[G.V()];
        arestaPara  = new Aresta[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marcado[v]) dfs(G, v);

        // checar se o digrafo tem um ciclo
        assert checar(G);
    }

    // checar se o algoritmo calcula outra ordenação topológico ou encontra um ciclo direcionado
    private void dfs(DigrafoPonderado G, int v) {
        naPilha[v] = true;
        marcado[v] = true;
        for (Aresta a : G.adj(v)) {
            int w = a.getV2();

            // curto circuito se for encontrado o ciclo direcionado
            if (ciclo != null) return;

            // encontra novo vértice, e chama dfs recursivamente
            else if (!marcado[w]) {
                arestaPara[w] = a;
                dfs(G, w);
            }

            // caminho do ciclo direcionado
            else if (naPilha[w]) {
                ciclo = new Pilha<Aresta>();

                Aresta f = a;
                while (f.getV1() != w) {
                    ciclo.empilha(f);
                    f = arestaPara[f.getV1()];
                }
                ciclo.empilha(f);

                return;
            }
        }

        naPilha[v] = false;
    }

    /**
     * O digrafo ponderado direcionado tem um ciclo direcionado?
     * @return verdadeiro se o digrafo ponderado tem um ciclo direcionado, ou falso, caso contrário
     */
    public boolean temCiclo() {
        return ciclo != null;
    }

    /**
     * Retorna o ciclo direcionado se o digrafo ponderado tem um ciclo direcionado, e null, caso contrário
     * @return o ciclo direcionado (como um iterable) se o digrafo ponderado tem um ciclo, e null, caso contrário
     */
    public Iterable<Aresta> ciclo() {
        return ciclo;
    }


    // certifica se o digrafo é acíclico ou tem um ciclo direcionado
    private boolean checar(DigrafoPonderado G) {

        // digrafo ponderado é cíclico
        if (temCiclo()) {
            // verifica ciclo
            Aresta primeiro = null, ultimo = null;
            for (Aresta a : ciclo()) {
                if (primeiro == null) primeiro = a;
                if (ultimo != null) {
                    if (ultimo.getV2() != a.getV1()) {
                        System.err.printf("arestas do ciclo %s e %s não incidentes\n", ultimo, a);
                        return false;
                    }
                }
                ultimo = a;
            }

            if (ultimo.getV2() != primeiro.getV1()) {
                System.err.printf("arestas do ciclo %s e %s não incidentes\n", ultimo, primeiro);
                return false;
            }
        }

        return true;
    }

    /**
     * Testa a classe AlgoritmoCicloDirecionado
     */
    public static void main(String[] args) {

        // create random DAG with V vertices and A edges; then add F random edges
        int V = Integer.parseInt(args[0]);
        int A = Integer.parseInt(args[1]);
        int F = Integer.parseInt(args[2]);
        DigrafoPonderado G = new DigrafoPonderado(V);
        
        /*
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < A; i++) {
            int v, w;
            do {
                v = StdRandom.uniform(V);
                w = StdRandom.uniform(V);
            } while (v >= w);
            double weight = StdRandom.uniform();
            G.addEdge(new DirectedEdge(v, w, weight));
        }

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = StdRandom.uniform(0.0, 1.0);
            G.addEdge(new DirectedEdge(v, w, weight));
        }

        StdOut.println(G);

        // find a directed ciclo
        AlgoritmoCicloDirecionado finder = new AlgoritmoCicloDirecionado(G);
        if (finder.temCiclo()) {
            StdOut.print("Cycle: ");
            for (DirectedEdge e : finder.ciclo()) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }

        // or give topologial sort
        else {
            StdOut.println("No directed cycle");
        }
        */
    }

}
