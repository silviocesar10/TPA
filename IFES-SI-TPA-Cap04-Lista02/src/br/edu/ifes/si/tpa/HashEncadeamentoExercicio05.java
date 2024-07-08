/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.si.tpa;

import java.util.Scanner;

/**
 *
 * @author java
 */
public class HashEncadeamentoExercicio05 {

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
    
    private static boolean existeNoVetor(String[] palavrasMesmoHasCode, int qtdPalavrasHashCodeIgual, String palavraAux){
        for (int i = 0; i < qtdPalavrasHashCodeIgual; i++) {
            if(palavrasMesmoHasCode[i].equals(palavraAux)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        String palavra = "";
        String palavraAux = "";
        int n, qtdPalavrasHashCodeIgual=0, hashCodePalavra = 0;
        
        System.out.println("Informe o valor de n: ");
        n = ler.nextInt();
        palavra = gerarStringAleatoria(n);
        hashCodePalavra = hashCode(palavra);

        System.out.println("Palavra: " + palavra);
        System.out.println("Hash Code da Palavra: " + hashCodePalavra);

        String[] palavrasMesmoHasCode = new String[n];
        
        while(qtdPalavrasHashCodeIgual < n){
            palavraAux = gerarStringAleatoria(n);
            if((hashCode(palavraAux) == hashCodePalavra) && (!palavraAux.equals(palavra)) && (!existeNoVetor(palavrasMesmoHasCode, qtdPalavrasHashCodeIgual, palavraAux))){
                palavrasMesmoHasCode[qtdPalavrasHashCodeIgual] = palavraAux;
                qtdPalavrasHashCodeIgual++;
            }
        }
        
        System.out.println("");
        for (int i = 0; i < palavrasMesmoHasCode.length; i++) {
            System.out.println(palavrasMesmoHasCode[i]);
        }

    }
}
