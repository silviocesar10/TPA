/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista4;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 20171si026
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       ArvoreBinariaBuscaBalanceada<Aplicativo> avl = new ArvoreBinariaBuscaBalanceada<Aplicativo>();
       Aplicativo ap1 = new Aplicativo("App-a", "Desenvolvedor a", 10);
       Aplicativo ap2 = new Aplicativo("App-b", "Desenvolvedor b", 10);
       Aplicativo ap3 = new Aplicativo("App-c", "Desenvolvedor c", 10);
       Aplicativo ap4 = new Aplicativo("App-d", "Desenvolvedor d", 10);
       Aplicativo ap5 = new Aplicativo("App-e", "Desenvolvedor e", 10);
       Aplicativo ap6 = new Aplicativo("App-f", "Desenvolvedor f", 10);
       Aplicativo ap7 = new Aplicativo("App-g", "Desenvolvedor g", 10);
       Aplicativo ap8 = new Aplicativo("App-h", "Desenvolvedor h", 10);
       Aplicativo ap9 = new Aplicativo("App-i", "Desenvolvedor i", 10);
       Aplicativo ap10 = new Aplicativo("App-j", "Desenvolvedor j", 10);
       
       avl.insere(ap8);
       avl.insere(ap9);
       avl.insere(ap3);
       avl.insere(ap4);
       avl.insere(ap5);
       avl.insere(ap6);
       avl.insere(ap7);
       avl.insere(ap10);
       avl.insere(ap1);
       avl.insere(ap2);
       
       avl.valoresEmOrdem().forEach(s ->{
           System.out.println(s);
       });
    }
    
}
