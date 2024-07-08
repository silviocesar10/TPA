/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.si.tpa.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author 20171si026
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    //função com algum problema
    /*private static char randomChar() {
        Random r = new Random();
        return (char)(r.nextInt(26) + 'A');
    }*/
    
      private static int hashCode(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31) + s.charAt(i);
        }
        return hash;
    }

    
    private static String gerarStringAleatoria(int qtdCaracteres) {
        String[] caracteres = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
        String palavra = "";
        for (int i = 0; i < qtdCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            palavra = palavra + caracteres[posicao];
        }
        return palavra;
    }
    //nao fas mais sentido estou usando lista  
    /*private static boolean existeNoVetor(String[] vec, String palavraAux){
        for (String s : vec) {
            if(s.equals(palavraAux)){
                return true;
            }
        }
        return false;
    }*/

    public static void encontraHashCode(){
        int n, qtd =0;
        String key = "";
        String tmp = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Por favor insira o tamanho da string");
        n = sc.nextInt();
        //String[] vc = new String[n];
        List<String> vc = new ArrayList<String>();
        key = gerarStringAleatoria(n);
        for(int i=0; i < n;){
           tmp = gerarStringAleatoria(n);
            if(hashCode(key) == hashCode(tmp) && /*!existeNoVetor(vc, tmp)*/ !vc.contains(tmp) ){
                //vc[i] = tmp;]
                vc.add(tmp);
                tmp= "";
                i++;
            }else{
                tmp= "";
                i = i;
                qtd++; 
                System.out.println("Falhas : " + qtd);
            }                       
        }
        System.out.println("Meu valor Chave " + key);
        for(String i : vc){
            System.out.println(i);
        }
        
        
    }
    public static void main(String[] args) {
           encontraHashCode();
    }
    
}
