package com.controle.ui;

import com.controle.dao.Conexao;
import com.controle.dao.ProjetoDao;
import com.controle.model.Projeto;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);

    Conexao conexao = new Conexao();
    Connection connection = conexao.conectarBanco();

    public void menu(){
        System.out.println("1. Projetos \n2. Tarefas");
        System.out.println("Escolha uma opção: ");
        int escolha = scanner.nextInt();

        switch (escolha){
            case 1:
                System.out.println("1. Criar projeto \n2. Listar projetos \n3. Atualizar projeto \n4. Deletar projeto");
                System.out.println("Escolha uma opção: ");
                int opcao = scanner.nextInt();

                scanner.nextLine();
                switch (opcao){
                    case 1:
                        System.out.println("Digite o nome do projeto: ");
                        String nome = scanner.nextLine();

                        System.out.println("Digite uma descrição para o projeto: ");
                        String descricao = scanner.nextLine();

                        LocalDate dataInicio = LocalDate.now();

                        LocalDate dataFim = dataInicio.plusDays(30);

                        Projeto projeto = new Projeto(nome, descricao, dataInicio, dataFim);
                        ProjetoDao projetoDao = new ProjetoDao(connection);
                        projetoDao.criarProjeto(projeto);
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
