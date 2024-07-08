/**
 * *****************************************************************************
 *  Compilação:        javac ArvoreBinariaBusca.java
 *  Execução:          java ArvoreBinariaBusca
 *
 *  Implementação de uma árvore binária de busca
 *
 *******************************************************************************
 */
package br.edu.ifes.tpa.silvio.exerc1;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Esta classe implementa uma Árvore Binária de Busca (Desbalanceada)
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/32bst/">Section 3.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class ArvoreBinariaBusca<Valor extends Comparable<Valor>> {

    private No raiz;                   // raíz da ArvoreBinariaBusca

    private class No {

        private Valor valor;           // classificada por valor
        private No esquerda, direita;  // subárvores da esquerda e direita
        private int tamanho;           // número de nós na subárvore

        public No(Valor valor, int tamanho) {
            this.valor = valor;
            this.tamanho = tamanho;
        }
    }

    /**
     * Inicialização vazia da ArvoreBinariaBusca.
     */
    public ArvoreBinariaBusca() {
    }

    /**
     * Retorna true se esta ArvoreBinariaBusca está vazia.
     * @return true se esta ArvoreBinariaBusca está vazia, ou false, caso contrário.
     */
    public boolean isVazia() {
        return tamanho() == 0;
    }

    /**
     * Retorna o número valores existente à partir da raíz da ArvoreBinariaBusca até x.
     * @return o número valores existente à partir da raíz da ArvoreBinariaBusca até x.
     */
    public int tamanho() {
        return tamanho(raiz);
    }

    private int tamanho(No x) {
        if (x == null) {
            return 0;
        } else {
            return x.tamanho;
        }
    }

    /**
     * Verifica se um determinado valor já existe na árvore.
     * @param valor o valor
     * @return verdadeiro, caso o valor exista, e falso, caso não exista.
     */
    public boolean busca(Valor valor) {
        return busca(raiz, valor);
    }

    private boolean busca(No x, Valor valor) {
        if (x == null) {
            return false;
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            return busca(x.esquerda, valor);
        } else if (cmp > 0) {
            return busca(x.direita, valor);
        } else {
            return true;
        }
    }
    public boolean buscaInterativa(Valor valor) {
        return buscaInterativa(raiz, valor);
    }
    private boolean buscaInterativa(No x, Valor valor) {
        No nRaiz = x;
        boolean achou = false;
        if (x == null) {
            return false;
        }
       while(!achou)
       {
           int cmp = valor.compareTo(nRaiz.valor);
           if (cmp < 0) {
                 nRaiz = x.esquerda;
           } else if (cmp > 0) {
                 nRaiz = x.direita;
           }else if(cmp == 0){
               achou = true;
           }else if(cmp == null){
               return false;
           }
        }
           return achou;
    }
    
    /**
     * Retorna todos os valores (em ordem) da ArvoreBinariaBusca no formato de Lista.
     * @return todos os valores (em ordem) da ArvoreBinariaBusca no formato de Lista.
     */
    public List<Valor> valoresEmOrdem() {
        List<Valor> lista = new ArrayList();
        valoresEmOrdem(raiz, lista);
        return lista;
    }

    private void valoresEmOrdem(No x, List<Valor> lista) {
        if (x == null) {
            return;
        }
        valoresEmOrdem(x.esquerda, lista);
        lista.add(x.valor);
        valoresEmOrdem(x.direita, lista);
    }

    /**
     * Retorna todos os valores (pré ordem) da ArvoreBinariaBusca no formato de Lista.
     * @return todos os valores (pré ordem) da ArvoreBinariaBusca no formato de Lista.
     */
    public List<Valor> valoresPreOrdem() {
        List<Valor> lista = new ArrayList();
        valoresPreOrdem(raiz, lista);
        return lista;
    }

    private void valoresPreOrdem(No x, List<Valor> lista) {
        if (x == null) {
            return;
        }
        lista.add(x.valor);
        valoresPreOrdem(x.esquerda, lista);
        valoresPreOrdem(x.direita, lista);
    }

    /**
     * Retorna todos os valores (pós ordem) da ArvoreBinariaBusca no formato de Lista.
     * @return todos os valores (pós ordem) da ArvoreBinariaBusca no formato de Lista.
     */
    public List<Valor> valoresPosOrdem() {
        List<Valor> lista = new ArrayList();
        valoresPosOrdem(raiz, lista);
        return lista;
    }

    private void valoresPosOrdem(No x, List<Valor> lista) {
        if (x == null) {
            return;
        }
        valoresPosOrdem(x.esquerda, lista);
        valoresPosOrdem(x.direita, lista);
        lista.add(x.valor);
    }

    /**
     * Insere um valor específico na árvore, sobreescrevendo o valor antigo, caso o valor seja igual a outro já inserido.
     * @param valor o valor
     * @throws NullPointerException se o valor é null
     */
    public void insere(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("valor para inserir é null");
        }
        raiz = insere(raiz, valor);
        assert checa();
    }

    private No insere(No x, Valor valor) {
        if (x == null) {
            return new No(valor, 1);
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            x.esquerda = insere(x.esquerda, valor);
        } else if (cmp > 0) {
            x.direita = insere(x.direita, valor);
        } else {
            x.valor = valor;
        }
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        return x;
    }
    
    /**
     * Remove um valor específico da árvore.
     * @param valor o valor
     * @throws NullPointerException se o valor é null
     */
    public void remove(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para remove() é null");
        }
        raiz = remove(raiz, valor);
        assert checa();
    }

    private No remove(No x, Valor valor) {
        if (x == null) {
            return null;
        }

        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            x.esquerda = remove(x.esquerda, valor);
        } else if (cmp > 0) {
            x.direita = remove(x.direita, valor);
        } else {
            if (x.direita == null) {
                return x.esquerda;
            }
            if (x.esquerda == null) {
                return x.direita;
            }
            No t = x;
            x = min(t.direita);
            x.direita = removeMin(t.direita);
            x.esquerda = t.esquerda;
        }
        x.tamanho = tamanho(x.esquerda) + tamanho(x.direita) + 1;
        return x;
    }

    /**
     * Remove o menor valor da árvore.
     * @throws NoSuchElementException se a árvore é vazia
     */
    public void removeMin() {
        if (isVazia()) {
            throw new NoSuchElementException("Árvore vazia");
        }
        raiz = removeMin(raiz);
        assert checa();
    }

    private No removeMin(No x) {
        if (x.esquerda == null) {
            return x.direita;
        }
        x.esquerda = removeMin(x.esquerda);
        x.tamanho = tamanho(x.esquerda) + tamanho(x.direita) + 1;
        return x;
    }

    /**
     * Remove o maior valor da árvore.
     * @throws NoSuchElementException se a árvore é vazia
     */
    public void removeMax() {
        if (isVazia()) {
            throw new NoSuchElementException("Árvore vazia");
        }
        raiz = removeMax(raiz);
        assert checa();
    }

    private No removeMax(No x) {
        if (x.direita == null) {
            return x.esquerda;
        }
        x.direita = removeMax(x.direita);
        x.tamanho = tamanho(x.esquerda) + tamanho(x.direita) + 1;
        return x;
    }

    /**
     * Retorna o menor valor da árvore.
     * @return o menor valor da árvore
     * @throws NoSuchElementException se a árvore é vazia
     */
    public Valor min() {
        if (isVazia()) {
            throw new NoSuchElementException("chamado min() com árvore vazia");
        }
        return min(raiz).valor;
    }

    private No min(No x) {
        if (x.esquerda == null) {
            return x;
        } else {
            return min(x.esquerda);
        }
    }

    /**
     * Retorna o maior valor da árvore.
     * @return o maior valor da árvore
     * @throws NoSuchElementException se a árvore é vazia
     */
    public Valor max() {
        if (isVazia()) {
            throw new NoSuchElementException("chamado max() com árvore vazia");
        }
        return max(raiz).valor;
    }

    private No max(No x) {
        if (x.direita == null) {
            return x;
        } else {
            return max(x.direita);
        }
    }

    /**
     * Retorna o maior valor na árvore, menor ou igual a um determinado valor passado como parâmetro.
     * @param valor o valor
     * @return o maior valor na árvore, menor ou igual a um determinado valor passado como parâmetro
     * @throws NoSuchElementException se não existe tal valor
     * @throws NullPointerException se o valor é null
     */
    public Valor piso(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para piso() é null");
        }
        if (isVazia()) {
            throw new NoSuchElementException("chamado piso() com árvore vazia");
        }
        No x = piso(raiz, valor);
        if (x == null) {
            return null;
        } else {
            return x.valor;
        }
    }

    private No piso(No x, Valor valor) {
        if (x == null) {
            return null;
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return piso(x.esquerda, valor);
        }
        No t = piso(x.direita, valor);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    /**
     * Retorna o menor valor na árvore, maior ou igual a um determinado valor passado como parâmetro.
     * @param valor o valor
     * @return o menor valor na árvore, maior ou igual a um determinado valor passado como parâmetro.
     * @throws NoSuchElementException se não existe tal valor
     * @throws NullPointerException se o valor é null
     */
    public Valor teto(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para teto() é null");
        }
        if (isVazia()) {
            throw new NoSuchElementException("chamado piso() com árvore vazia");
        }
        No x = teto(raiz, valor);
        if (x == null) {
            return null;
        } else {
            return x.valor;
        }
    }

    private No teto(No x, Valor valor) {
        if (x == null) {
            return null;
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            No t = teto(x.esquerda, valor);
            if (t != null) {
                return t;
            } else {
                return x;
            }
        }
        return teto(x.direita, valor);
    }

    /**
     * Retorna o número de valores na árvore estritamente menores que um determinado valor passado como parâmetro.
     * @param valor o valor
     * @return o número de valores na árvore estritamente menores que um determinado valor passado como parâmetro.
     * @throws NullPointerException se o valor é null
     */
    public int posto(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento para posto() é null");
        }
        return posto(raiz, valor);
    }

    private int posto(No x, Valor valor) {
        if (x == null) {
            return 0;
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            return posto(x.esquerda, valor);
        } else if (cmp > 0) {
            return 1 + tamanho(x.esquerda) + posto(x.direita, valor);
        } else {
            return tamanho(x.esquerda);
        }
    }


    /**
     * Retorna a altura da árvore
     * @return a altura da árvore (uma árvore com um único nó tem altura 0)
     */
    public int altura() {
        return altura(raiz);
    }

    private int altura(No x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(altura(x.esquerda), altura(x.direita));
    }

    /**
     * *************************************************************************
     * Checando a integridade da estrutura de dados ArvoreBinariaBusca.
     * *************************************************************************
     */
    private boolean checa() {
        if (!isBST()) {
            System.out.println("Valores em ordem não simétrica");
        }
        if (!isTamanhoConsistente()) {
            System.out.println("Contagem das subárvores não consitente");
        }
        return isBST() && isTamanhoConsistente();
    }

    // a árvore binária satisfaz a ordem simétrica?
    // Nota: Este teste também garante que a estrutura de dados é uma árvore binária
    private boolean isBST() {
        return isBST(raiz, null, null);
    }

    // é uma árvore binária com raíz em x quanto todos os valores estão estritamente entre min e max
    // (se min ou max for null, tratar restrição)
    private boolean isBST(No x, Valor min, Valor max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.valor.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.valor.compareTo(max) >= 0) {
            return false;
        }
        return isBST(x.esquerda, min, x.valor) && isBST(x.direita, x.valor, max);
    }

    // O tamanho dos campos está correto?
    private boolean isTamanhoConsistente() {
        return isTamanhoConsistente(raiz);
    }

    private boolean isTamanhoConsistente(No x) {
        if (x == null) {
            return true;
        }
        if (x.tamanho != tamanho(x.esquerda) + tamanho(x.direita) + 1) {
            return false;
        }
        return isTamanhoConsistente(x.esquerda) && isTamanhoConsistente(x.direita);
    }

    /**
     * Testa a classe ArvoreBinariaBusca
     */
    public static void main(String[] args) {
        //Declarando uma árvore com valores do tipo string
        ArvoreBinariaBusca<String> bst = new ArvoreBinariaBusca<String>();

        bst.insere("S");
        bst.insere("E");
        bst.insere("A");
        bst.insere("R");
        bst.insere("C");
        bst.insere("H");
        bst.insere("M");
        bst.insere("X");

        System.out.println();

        for (String s : bst.valoresEmOrdem()) {
            Valor v = (Valor) s;
            if(buscaInterativa(v)){
                System.out.println("Achou " + s);
            }else{
                System.out.println("Nao Achou :(");
            }
        }
        
        
    }
}