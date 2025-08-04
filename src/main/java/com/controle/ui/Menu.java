package com.controle.ui;

import com.controle.dao.ProjetoDao;
import com.controle.dao.TarefaDao;
import com.controle.model.Projeto;
import com.controle.model.Tarefa;

import java.security.Signature;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Connection connection;
    ProjetoDao projetoDao;
    TarefaDao tarefaDao;

    public Menu(Connection connection) {
        this.connection = connection;
        this.projetoDao = new ProjetoDao(connection);
        this.tarefaDao = new TarefaDao(connection);
    }

    public void menu() {
        boolean i = true;

        while (i) {
            System.out.println("\n1. Projetos \n2. Tarefas \n3. Encerrar programa");
            System.out.println("\nEscolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.println(
                            "\n1. Criar projeto \n2. Listar projetos \n3. Listar projetos com tarefas \n4. Estatísticas por status \n5. Calcular progresso \n6. Atualizar projeto \n7. Deletar projeto");
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
                                System.out.println("\nNenhum projeto encontrado");
                            } else {
                                System.out.printf("Total de projetos encontrados: %d%n%n", projetos.size());
                                
                                for (Projeto p : projetos) {
                                    System.out.printf("Projeto ID: %d%n", p.getProjetoId() != null ? p.getProjetoId() : 0);
                                    System.out.printf("Nome: %s%n", p.getNome() != null ? p.getNome() : "N/A");
                                    System.out.printf("Descrição: %s%n", p.getDescricao() != null ? p.getDescricao() : "N/A");
                                    System.out.printf("Período: %s até %s%n", 
                                        p.getDataInicio() != null ? p.getDataInicio().toString() : "N/A",
                                        p.getDataFim() != null ? p.getDataFim().toString() : "N/A");
                                    System.out.printf("Status: %s%n\n", p.getStatus() != null ? p.getStatus() : "N/A\n");
                                }
                            }
                            break;

                        case 3:
                            Map<String, List<String>> projetosComTarefas = projetoDao.listarProjetoComTarefasRelacionadas();
                            if (projetosComTarefas.isEmpty()) {
                                System.out.println("Nenhum projeto encontrado");
                            } else {
                                for (Map.Entry<String, List<String>> entry : projetosComTarefas.entrySet()) {
                                    String nomeProjeto = entry.getKey();
                                    List<String> tarefas = entry.getValue();

                                    System.out.println("\nProjeto: " + nomeProjeto);
                                    for (String tarefa : tarefas) {
                                        System.out.println("  - " + tarefa);
                                    }
                                }
                            }
                            break;

                        case 4:
                            Map<String, Integer> estatisticas = projetoDao.obterEstatisticasPorStatus();
                            if (estatisticas.isEmpty()) {
                                System.out.println("Nenhum projeto encontrado");
                            } else {
                                for (Map.Entry<String, Integer> entry : estatisticas.entrySet()) {
                                    System.out.println("Status: " + entry.getKey() + " - Quantidade: " + entry.getValue());
                                }
                            }
                            break;

                        case 5:
                            System.out.println("Digite o ID do projeto para calcular o progresso: ");
                            int projetoId = scanner.nextInt();
                            double progresso = projetoDao.calcularProgressoProjeto(projetoId);
                            
                            System.out.println("ID do Projeto: " + projetoId);
                            System.out.println("Progresso: " + String.format("%.2f", progresso) + "%");
                            
                            int barraCompleta = (int) (progresso / 5); 
                            System.out.print("Barra: [");
                            for (int j = 0; j < 20; j++) {
                                if (j < barraCompleta) {
                                    System.out.print("█");
                                } else {
                                    System.out.print("░");
                                }
                            }
                            System.out.println("] " + String.format("%.1f", progresso) + "%");
                            break;

                        case 6:
                            List<Projeto> projetos1 = projetoDao.buscarProjetos();

                            System.out.println("Qual o ID do projeto que deseja atualizar? ");
                            int ID = scanner.nextInt();

                            System.out.println("Digite o novo nome do projeto: ");
                            String novoNome = scanner.nextLine();

                            System.out.println("Digite a nova descrição do projeto: ");
                            String novaDesc = scanner.nextLine();

                            System.out.println();
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
                            scanner.nextLine();

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
