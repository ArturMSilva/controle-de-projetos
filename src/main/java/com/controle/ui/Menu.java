package com.controle.ui;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);

    public void menu(){
        System.out.println("1. Projetos \n2. Tarefas");
        System.out.println("Escolha uma opção: ");
        int escolha = scanner.nextInt();

        switch (escolha){
            case 1:
                System.out.println("1. Criar projeto \n2. Listar projetos \n3. Atualizar projeto \n4. Deletar projeto");
                System.out.println("Escolha uma opção: ");
                int opcao = scanner.nextInt();

                switch (opcao){

                }
                break;

            case 2:
                //
                break;

            default:
                System.out.println("Opção inválida...");
                break;
        }
    }
}
