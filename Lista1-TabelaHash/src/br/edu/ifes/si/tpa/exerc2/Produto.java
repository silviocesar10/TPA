/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.si.tpa.exerc2;

/**
 *
 * @author 20171si026
 */
public class Produto {
    private String nome;
    private int preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }
    
     public int hashCode(){
         return this.getNome().hashCode();
     }

    public Produto(String nome, int preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return  nome + ", " + preco;
    }
     
     
}
