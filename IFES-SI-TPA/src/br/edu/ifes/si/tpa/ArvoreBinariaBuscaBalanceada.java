/**
 * *****************************************************************************
 *  Compilação:        javac ArvoreBinariaBuscaBalanceada.java
 *  Execução:          java ArvoreBinariaBuscaBalanceada
 *
 *  Implementação de uma árvore binária de busca balanceada
 *
 *******************************************************************************
 */
package br.edu.ifes.si.tpa;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArvoreBinariaBuscaBalanceada<Valor extends Comparable<Valor>> {

    private No raiz;

    private class No {

        private Valor valor;     // o valor
        private int altura;      // altura da subárvore
        private int tamanho;     // número de nós na subárvore
        private No esquerda;     // subárvore esquerda
        private No direita;      // subárvore direita

        public No(Valor valor, int altura, int tamanho) {
            this.valor = valor;
            this.tamanho = tamanho;
            this.altura = altura;
        }
    }

    public ArvoreBinariaBuscaBalanceada() {
    }

    /**
     * Retorna true se esta ArvoreBinariaBuscaBalanceada está vazia.
     * @return true se esta ArvoreBinariaBuscaBalanceada está vazia, ou false, caso contrário.
     */
    public boolean isVazia() {
        return raiz == null;
    }

    /**
     * Retorna o número valores existente à partir da raíz da ArvoreBinariaBuscaBalanceada até x.
     * @return o número valores existente à partir da raíz da ArvoreBinariaBuscaBalanceada até x.
     */
    public int tamanho() {
        return tamanho(raiz);
    }

    private int tamanho(No x) {
        if (x == null) {
            return 0;
        }
        return x.tamanho;
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
        return x.altura;
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
    
    /**
     * Retorna todos os valores (em ordem) da ArvoreBinariaBuscaBalanceada no formato de Lista.
     * @return todos os valores (em ordem) da ArvoreBinariaBuscaBalanceada no formato de Lista.
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
     * Retorna todos os valores (pré ordem) da ArvoreBinariaBuscaBalanceada no formato de Lista.
     * @return todos os valores (pré ordem) da ArvoreBinariaBuscaBalanceada no formato de Lista.
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
        valoresEmOrdem(x.esquerda, lista);
        valoresEmOrdem(x.direita, lista);
    }

    /**
     * Retorna todos os valores (pós ordem) da ArvoreBinariaBuscaBalanceada no formato de Lista.
     * @return todos os valores (pós ordem) da ArvoreBinariaBuscaBalanceada no formato de Lista.
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
        valoresEmOrdem(x.esquerda, lista);
        valoresEmOrdem(x.direita, lista);
        lista.add(x.valor);
    }

    /**
     * Insere um valor específico na árvore, sobreescrevendo o valor antigo, caso o valor seja igual a outro já inserido.
     * @param valor o valor
     * @throws NullPointerException se o valor é null
     */
    public void insere(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("valor para inserir é nulo");
        }
        raiz = insere(raiz, valor);
        assert checa();
    }

    private No insere(No x, Valor valor) {
        if (x == null) {
            return new No(valor, 0, 1);
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            x.esquerda = insere(x.esquerda, valor);
        } else if (cmp > 0) {
            x.direita = insere(x.direita, valor);
        } else {
            x.valor = valor;
            return x;
        }
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        return balancea(x);
    }

    /**
     * Restaura a propriedade AVL da subárvore
     * @param x a subárvore
     * @return a subárvore com a propriedade AVL restaurada
     */
    private No balancea(No x) {
        if (fatorBalanceamento(x) < -1) {
            if (fatorBalanceamento(x.direita) > 0) {
                x.direita = rotacionaDireita(x.direita);
            }
            x = rotacionaEsquerda(x);
        } else if (fatorBalanceamento(x) > 1) {
            if (fatorBalanceamento(x.esquerda) < 0) {
                x.esquerda = rotacionaEsquerda(x.esquerda);
            }
            x = rotacionaDireita(x);
        }
        return x;
    }

    /**
     * Retorna o fator de balanceamento da subárvore . O fator de balanceamento é
     * definido como a diferença na altura da subárvore da esquerda e da direita
     * , nesta ordem. Portanto, uma subárvore com um fator de balanceamento de
     * -1 , 0 ou 1 tem a propriedade AVL, uma vez que as alturas das subárvores
     * filhas diferem por no máximo um 
     * @param x a subárvore
     * @return o fator de balanceamento da subárvore
     */
    private int fatorBalanceamento(No x) {
        return altura(x.esquerda) - altura(x.direita);
    }

    /**
     * Rotaciona para a direita uma dada subárvore.
     * @param x a subárvore
     * @return a subárvore a direita rotacionada
     */
    private No rotacionaDireita(No x) {
        No y = x.esquerda;
        x.esquerda = y.direita;
        y.direita = x;
        y.tamanho = x.tamanho;
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        return y;
    }

    /**
     * Rotaciona para a esquerda uma dada subárvore.
     * @param x a subárvore
     * @return a subárvore a esquerda rotacionada
     */
    private No rotacionaEsquerda(No x) {
        No y = x.direita;
        x.direita = y.esquerda;
        y.esquerda = x;
        y.tamanho = x.tamanho;
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        return y;
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
        if (!busca(valor)) {
            return;
        }
        raiz = remove(raiz, valor);
        assert checa();
    }

    private No remove(No x, Valor valor) {
        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            x.esquerda = remove(x.esquerda, valor);
        } else if (cmp > 0) {
            x.direita = remove(x.direita, valor);
        } else if (x.esquerda == null) {
            return x.direita;
        } else if (x.direita == null) {
            return x.esquerda;
        } else {
            No y = x;
            x = min(y.direita);
            x.direita = removeMin(y.direita);
            x.esquerda = y.esquerda;
        }
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        return balancea(x);
    }

    /**
     * Remove o menor valor da árvore.
     * @throws NoSuchElementException se a árvore é vazia
     */
    public void removeMin() {
        if (isVazia()) {
            throw new NoSuchElementException("called deleteMin() with empty symbol table");
        }
        raiz = removeMin(raiz);
        assert checa();
    }

    private No removeMin(No x) {
        if (x.esquerda == null) {
            return x.direita;
        }
        x.esquerda = removeMin(x.esquerda);
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        return balancea(x);
    }

    /**
     * Remove o maior valor da árvore.
     * @throws NoSuchElementException se a árvore é vazia
     */
    public void removeMax() {
        if (isVazia()) {
            throw new NoSuchElementException("Arvore vazia");
        }
        raiz = removeMax(raiz);
        assert checa();
    }

    private No removeMax(No x) {
        if (x.direita == null) {
            return x.esquerda;
        }
        x.direita = removeMax(x.direita);
        x.tamanho = 1 + tamanho(x.esquerda) + tamanho(x.direita);
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        return balancea(x);
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
        }
        return min(x.esquerda);
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
        }
        return max(x.direita);
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
        No y = piso(x.direita, valor);
        if (y != null) {
            return y;
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
            throw new NullPointerException("argumento para teto() é nulo");
        }
        if (isVazia()) {
            throw new NoSuchElementException("chamada teto() com árvore vazia");
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
        if (cmp > 0) {
            return teto(x.direita, valor);
        }
        No y = teto(x.esquerda, valor);
        if (y != null) {
            return y;
        } else {
            return x;
        }
    }

    /**
     * Retorna o número de valores na árvore estritamente menores que um determinado valor passado como parâmetro.
     * @param valor o valor
     * @return o número de valores na árvore estritamente menores que um determinado valor passado como parâmetro.
     * @throws NullPointerException se o valor é null
     */
    public int posto(Valor valor) {
        if (valor == null) {
            throw new NullPointerException("argumento posto() é nulo");
        }
        return posto(valor, raiz);
    }

    private int posto(Valor valor, No x) {
        if (x == null) {
            return 0;
        }
        int cmp = valor.compareTo(x.valor);
        if (cmp < 0) {
            return posto(valor, x.esquerda);
        } else if (cmp > 0) {
            return 1 + tamanho(x.esquerda) + posto(valor, x.direita);
        } else {
            return tamanho(x.esquerda);
        }
    }
    
    /**
     * Retorna o número valores existente à partir da raíz da ArvoreBinariaBuscaBalanceada até x.
     * @return o número valores existente à partir da raíz da ArvoreBinariaBuscaBalanceada até x.
     */
    public int tamanho(Valor min, Valor max) {
        if (min == null) {
            throw new NullPointerException("primeiro argumento para tamanho() é null");
        }
        if (max == null) {
            throw new NullPointerException("segundo argumento para tamanho() é null");
        }
        if (min.compareTo(max) > 0) {
            return 0;
        }
        if (busca(max)) {
            return posto(max) - posto(min) + 1;
        } else {
            return posto(max) - posto(min);
        }
    }

    /**
     * *************************************************************************
     * Checando a integridade da estrutura de dados ArvoreBinariaBuscaBalanceada.
     * *************************************************************************
     */
    private boolean checa() {
        if (!isBST()) {
            System.out.println("Valores em ordem não simétrica");
        }
        if (!isAVL()) {
            System.out.println("Propriedade AVL não consistente");
        }
        if (!isTamanhoConsistente()) {
            System.out.println("Tamanho de subárvore não consistente");
        }
        return isBST() && isAVL() && isTamanhoConsistente();
    }

    /**
     * Checa se a propriedade AVL é consistente.
     * @return verdadeiro se a propriedade AVL é consistente.
     */
    private boolean isAVL() {
        return isAVL(raiz);
    }

    private boolean isAVL(No x) {
        if (x == null) {
            return true;
        }
        int bf = fatorBalanceamento(x);
        if (bf > 1 || bf < -1) {
            return false;
        }
        return isAVL(x.esquerda) && isAVL(x.direita);
    }

    /**
     * Checa se a propriedade BST é consistente.
     * @return verdadeiro se a propriedade BST é consistente.
     */
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
     * Testa a classe ArvoreBinariaBuscaBalanceada
     */
    public static void main(String[] args) {
        ArvoreBinariaBuscaBalanceada<Integer> avl = new ArvoreBinariaBuscaBalanceada<Integer>();

        avl.insere(10);
        avl.insere(12);
        avl.insere(5);
        avl.insere(22);
        avl.insere(3);
        avl.insere(15);
        avl.insere(100);
        
        
        System.out.println();

        avl.valoresEmOrdem().forEach((s) -> {
            System.out.println(s + " ");
        });
    }
}
