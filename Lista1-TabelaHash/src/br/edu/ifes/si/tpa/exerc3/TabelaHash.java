/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.si.tpa.exerc3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 20171si026
 */
public class TabelaHash<Valor> {
    
    
    //private static final int INIT_CAPACITY = 4;

    private int n;             // número de valores
    private static final int m = 100;
    // vetor de lista de valores
     // tamanho da hash table
    private List<Valor>[] st;  


    /**
     * Inicializa uma tabela hash vazia com m listas.
     *
     * @param m o inicial número de listas
     */
    public TabelaHash() {
        //this(INIT_CAPACITY);
        st = (List<Valor>[]) new List[this.m];
        for (int i = 0; i < m; i++) {
            st[i] = new ArrayList<Valor>();
        }
    }

    /**
     * Redimenciona a tabela hash para ter um dado número de listas, realizando
     * novamente o hash de todos os valores.
     *
     * @param listas o número de listas armazenadas no vetor da tabela hash
     */
    /*
    private void redimensiona(int listas) {
        //TabelaHash<Valor> temp = new TabelaHash<Valor>(listas);
        TabelaHash<Valor> temp = new TabelaHash<Valor>();
        for (int i = 0; i < m; i++) {
            for (Valor valor : st[i]) {
                temp.insere(valor);
            }
        }
        this.n = temp.n;
        this.st = temp.st;
    }
    */

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
        return st[i].contains(i);
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
        //if (n >= 10 * m) {
            //redimensiona(2 * m);
        //}

        int i = hash(valor);
        if (!st[i].contains(valor)) { //nesta implementação, não estamos inserindo valores repetidos
            n++;
            st[i].add(0, valor); // inserindo o elemento na primeira posição da lista
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
        if (st[i].contains(valor)) {
            n--;
            st[i].remove(valor);
        }

        // reduz à metade o tamanho da tabela hash se o tamanho médio da lista <= 2
        //if (m > INIT_CAPACITY && n <= 2 * m) {
            //redimensiona(m / 2);
        //}
    }

    /**
     * Retorna os valores da tabela hash no formato de Lista.
     *
     * @return os valores da tabela hash no formato de Lista
     */
    public List<Valor> valores() {
        List<Valor> lista = new ArrayList();
        for (int i = 0; i < m; i++) {
            for (Valor valor : st[i]) {
                lista.add(0, valor); // inserindo o valor na primeira posição da lista
            }
        }
        return lista;
    }
    public void imprime(){
         for (int i = 0; i < m; i++) {
            System.out.print("[" + i + "]");
            for (Valor valor : st[i]) {
                System.out.print(" " + valor);
            }
            System.out.println();
        }
    }
}
