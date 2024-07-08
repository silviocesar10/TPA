/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista4;

/**
 *
 * @author 20171si026
 */
public class Aplicativo implements Comparable<Aplicativo> {
    private String nome;
    private String desenvolvedor;
    private int memoria;

    @Override
    public int compareTo(Aplicativo a) {
        return this.nome.compareTo(a.nome);
    }
    

    @Override
    public String toString(){
        return this.nome;
    }
    
    public Aplicativo(String n, String des, int m){
        this.nome = n;
        this.desenvolvedor = des;
        this.memoria = m;
    }
    
}
