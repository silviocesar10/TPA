/**
 * *****************************************************************************
 *  Compilação:        javac HashEncadeamento.java
 *  Execução:          java HashEncadeamento
 *
 *  Implementação de uma tabela hash com encadeamento separado
 *
 *******************************************************************************
 */
package br.edu.ifes.si.tpa;

import java.util.List;
import java.util.ArrayList;

/**
 * Esta classe implementa uma Tabela Hash utilizando encadeamento encadeamento separado
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/34hash/">Section 3.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class HashEncadeamentoExercicio06<Valor extends Comparable<Valor>> {

    private static final int INIT_CAPACITY = 4;

    private int n;             // número de valores
    private int m;             // tamanho da hash table
    private ArvoreBinariaBuscaBalanceada<Valor>[] st;  // vetor de AVL de valores
    //ArvoreBinariaBuscaBalanceada<String> avl = new ArvoreBinariaBuscaBalanceada<String>();

    /**
     * Inicializa uma tabela hash vazia
     */
    public HashEncadeamentoExercicio06() {
        this(INIT_CAPACITY);
    }

    /**
     * Inicializa uma tabela hash vazia com m listas.
     *
     * @param m o inicial número de listas
     */
    public HashEncadeamentoExercicio06(int m) {
        this.m = m;
        st = (ArvoreBinariaBuscaBalanceada<Valor>[]) new ArvoreBinariaBuscaBalanceada[m];
        for (int i = 0; i < m; i++) {
            st[i] = new ArvoreBinariaBuscaBalanceada<>();
        }
    }

    /**
     * Redimenciona a tabela hash para ter um dado número de listas, realizando
     * novamente o hash de todos os valores.
     *
     * @param listas o número de listas armazenadas no vetor da tabela hash
     */
    private void redimensiona(int listas) {
        HashEncadeamentoExercicio06<Valor> temp = new HashEncadeamentoExercicio06<Valor>(listas);
        for (int i = 0; i < m; i++) {
            for (Valor valor : st[i].valoresEmOrdem()) {
                temp.insere(valor);
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }

    /**
     * Retorna o código hash (entre 0 e m-1) para um dado valor da tabela hash.
     *
     * @return o código hash para um dado valor da tabela hash.
     */
    private int hash(Valor valor) {
        return (valor.hashCode() & 0x7fffffff) % m;
    }

    /**
     * Retorna o número de valores da tabela hash.
     *
     * @return o número de valores da tabela hash
     */
    public int tamanho() {
        return n;
    }

    /**
     * Retorna true se esta tabela hash está vazia.
     *
     * @return true se esta tabela hash está vazia; false caso contrário
     */
    public boolean isVazia() {
        return tamanho() == 0;
    }

    /**
     * Retorna true se esta tabela hash contém um específico valor.
     *
     * @param valor o valor
     * @return true se esta tabela hash contém o valor 'valor'; false caso
     * contrário
     * @throws NullPointerException se valor é null
     */
    public boolean contem(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para contem() é null");
        }
        int i = hash(valor);
        return st[i].busca(valor);
    }

    /**
     * Insere um valor específico na tabela hash.
     *
     * @param valor o valor
     * @throws NullPointerException se valor é null
     */
    public void insere(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para insere() é null");
        }

        // dobra o tamanho da tabela hash se o tamanho médio da lista >= 10
        if (n >= 10 * m) {
            redimensiona(2 * m);
        }

        int i = hash(valor);
        if (!st[i].busca(valor)) { //nesta implementação, não estamos inserindo valores repetidos
            n++;
            st[i].insere(valor); // inserindo o elemento na primeira posição da lista
        }
    }

    /**
     * Remove um valor específico desta tabela hash (se o valor estiver na
     * tabela hash).
     *
     * @param valor o valor
     * @throws NullPointerException se o valor é null
     */
    public void remove(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para remove() é null");
        }

        int i = hash(valor);
        if (st[i].busca(valor)) {
            n--;
            st[i].remove(valor);
        }

        // reduz à metade o tamanho da tabela hash se o tamanho médio da lista <= 2
        if (m > INIT_CAPACITY && n <= 2 * m) {
            redimensiona(m / 2);
        }
    }

    /**
     * Retorna os valores da tabela hash no formato de Lista.
     *
     * @return os valores da tabela hash no formato de Lista
     */
    public List<Valor> valores() {
        List<Valor> lista = new ArrayList();
        for (int i = 0; i < m; i++) {
            for (Valor valor : st[i].valoresEmOrdem()) {
                lista.add(0, valor); // inserindo o valor na primeira posição da lista
            }
        }
        return lista;
    }

    /**
     * Testa a classe HashEncadeamento.
     */
    public static void main(String[] args) {
        HashEncadeamentoExercicio06<Integer> hash = new HashEncadeamentoExercicio06<Integer>();
        
        for (int i = 1; i <= 5; i++) {
            hash.insere(i);
        }

        // imprimindo valores valores
        for (Integer i : hash.valores()) {
            System.out.println(i + " ");
        }

        // verificando a existência de valores na tabela hash
        System.out.println("contem(4) : " + hash.contem(4));
        System.out.println("contem(10): " + hash.contem(10));

    }

}
