package com.controle.ui;

import com.controle.dao.Conexao;
import com.controle.dao.ProjetoDao;
import com.controle.dao.TarefaDao;
import com.controle.model.Projeto;
import com.controle.model.Tarefa;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Conexao conexao = new Conexao();
    Connection connection = conexao.conectarBanco();
    ProjetoDao projetoDao = new ProjetoDao(connection);
    TarefaDao tarefaDao = new TarefaDao(connection);

    public void menu() {
        boolean i = true;

        while (i) {
            System.out.println("1. Projetos \n2. Tarefas \n3. Encerrar programa");
            System.out.println("Escolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.println(
                            "1. Criar projeto \n2. Listar projetos \n3. Atualizar projeto \n4. Deletar projeto");
                    System.out.println("Escolha uma opção: ");
                    int opcaoProjeto = scanner.nextInt();

                    scanner.nextLine();
                    switch (opcaoProjeto) {
                        case 1:
                            System.out.println("Digite o nome do projeto: ");
                            String nome = scanner.nextLine();

                            System.out.println("Digite uma descrição para o projeto: ");
                            String descricao = scanner.nextLine();

                            LocalDate dataInicio = LocalDate.now();

                            System.out.println("Digite a duração do projeto (dias): ");
                            int duracaoProjeto = scanner.nextInt();

                            LocalDate dataFim = dataInicio.plusDays(duracaoProjeto);

                            Projeto projeto = new Projeto(nome, descricao, dataInicio, dataFim);
                            projetoDao.criarProjeto(projeto);
                            break;

                        case 2:
                            List<Projeto> projetos = projetoDao.buscarProjetos();
                            if (projetos.isEmpty()) {
                                System.out.println("Nenhum projeto encontrado");
                            } else {
                                for (Projeto p : projetos) {
                                    System.out.println(p.toString());
                                }
                            }
                            break;
                    }
                    break;

                case 2:
                    System.out.println(
                            "1. Criar tarefa \n2. Listar tarefas \n3. Atualizar tarefa \n4. Deletar tarefa");
                    System.out.println("Escolha uma opção: ");
                    int opcaoTarefa = scanner.nextInt();

                    scanner.nextLine();
                    switch (opcaoTarefa) {
                        case 1:
                            Tarefa tarefa = new Tarefa();
                            System.out.println("Digite o ID do projeto: ");
                            tarefa.setProjetoId(scanner.nextInt());

                            System.out.println("Digite o título da tarefa: ");
                            tarefa.setTitulo(scanner.nextLine());

                            System.out.println("Digite o responsável pela tarefa: ");
                            tarefa.setResponsavel(scanner.nextLine());

                            System.out.println("Digite o prazo da tarefa (yyyy-MM-dd): ");
                            tarefa.setPrazo(LocalDate.parse(scanner.nextLine()));

                            tarefaDao.criarTarefa(tarefa);
                            break;

                        case 2:
                            System.out.println("Encerrando programa...");
                            i = false;
                            break;

                        case 3:
                            System.out.println("Encerrando programa...");
                            i = false;
                            break;

                        default:
                            System.out.println("Opção inválida...");
                            break;
                    }
            }
        }
    }
}
