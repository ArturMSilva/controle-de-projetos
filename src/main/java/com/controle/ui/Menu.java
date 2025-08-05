package com.controle.ui;

import com.controle.dao.HistoricoAlteracaoDao;
import com.controle.dao.ProjetoDao;
import com.controle.dao.TarefaDao;
import com.controle.model.HistoricoAlteracao;
import com.controle.model.Projeto;
import com.controle.model.Tarefa;

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
    HistoricoAlteracaoDao historicoDao;

    public Menu(Connection connection) {
        this.connection = connection;
        this.projetoDao = new ProjetoDao(connection);
        this.tarefaDao = new TarefaDao(connection);
        this.historicoDao = new HistoricoAlteracaoDao(connection);
    }

    public void menu() {
        boolean i = true;

        while (i) {
            System.out.println("\n1. Projetos \n2. Tarefas \n3. Histórico de Alterações \n4. Encerrar programa");
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

                            for (Projeto p : projetos1){
                                System.out.printf("Projeto ID: %d%n", p.getProjetoId() != null ? p.getProjetoId() : 0);
                                System.out.printf("Nome: %s%n", p.getNome() != null ? p.getNome() : "N/A");
                            }

                            System.out.println("Digite o id do projeto que deseja atualizar: ");
                            int ID = scanner.nextInt();
                            scanner.nextLine();

                            Projeto projetoSelecionado = null;
                            for (Projeto p : projetos1){
                                if (p.getProjetoId() == ID){
                                    projetoSelecionado = p;
                                    break;
                                }
                            }

                            if (projetoSelecionado != null){
                                System.out.println("Digite o novo nome do projeto: ");
                                String novoNome = scanner.nextLine();

                                System.out.println("Digite a nova descrição do projeto: ");
                                String novaDescricao = scanner.nextLine();

                                LocalDate data = projetoSelecionado.getDataInicio();

                                System.out.println("Digite a quantidade de dias a serem adicionados ao prazo: ");
                                int dias = scanner.nextInt();
                                scanner.nextLine();

                                LocalDate novoPrazo = data.plusDays(dias);

                                System.out.println("Digite o novo status do projeto: ");
                                String novoStatus = scanner.nextLine();

                                Projeto projetoAtualizado = new Projeto(novoNome, novaDescricao, data, novoPrazo);
                                projetoAtualizado.setStatus(novoStatus);

                                projetoDao.atualizaProjeto(projetoAtualizado, ID);
                            }else {
                                System.out.println("Projeto não encontrado");
                            }

                            break;

                        case 7:
                            List<Projeto> projetos2 = projetoDao.buscarProjetos();

                            for (Projeto p : projetos2){
                                System.out.printf("Projeto ID: %d%n", p.getProjetoId() != null ? p.getProjetoId() : 0);
                                System.out.printf("Nome: %s%n", p.getNome() != null ? p.getNome() : "N/A");
                            }

                            System.out.println("Digite o id do projeto que deseja deletar: ");
                            int id = scanner.nextInt();

                            System.out.println("Projeto deletado com sucesso");
                            projetoDao.deletarProjeto(id);
                    }
                    break;

                case 2:
                    System.out.println("\n1. Criar tarefa \n2. Listar tarefas \n3. Atualizar tarefa \n4. Deletar tarefa \n5. Voltar ao menu principal");
                    System.out.println("Escolha uma opção: ");
                    int opcaoTarefa = scanner.nextInt();

                    scanner.nextLine();
                    switch (opcaoTarefa) {
                        case 1:
                            List<Projeto> projetosDisponiveis = projetoDao.buscarProjetos();
                            if (projetosDisponiveis.isEmpty()) {
                                System.out.println("Nenhum projeto encontrado. É necessário criar um projeto primeiro.");
                                break;
                            }
                            
                            System.out.println("\nProjetos disponíveis:");
                            for (Projeto p : projetosDisponiveis) {
                                System.out.printf("ID: %d - Nome: %s%n", 
                                    p.getProjetoId() != null ? p.getProjetoId() : 0,
                                    p.getNome() != null ? p.getNome() : "N/A");
                            }
                            
                            Tarefa tarefa = new Tarefa();
                            System.out.println("\nDigite o ID do projeto: ");
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
                            List<Tarefa> tarefas = tarefaDao.buscarTarefas();
                            if (tarefas.isEmpty()) {
                                System.out.println("\nNenhuma tarefa encontrada");
                            } else {
                                System.out.printf("Total de tarefas encontradas: %d%n%n", tarefas.size());
                                
                                for (Tarefa t : tarefas) {
                                    System.out.printf("Tarefa ID: %d%n", t.getTarefaId() != null ? t.getTarefaId() : 0);
                                    System.out.printf("Projeto ID: %d%n", t.getProjetoId() != null ? t.getProjetoId() : 0);
                                    System.out.printf("Título: %s%n", t.getTitulo() != null ? t.getTitulo() : "N/A");
                                    System.out.printf("Responsável: %s%n", t.getResponsavel() != null ? t.getResponsavel() : "N/A");
                                    System.out.printf("Prazo: %s%n", t.getPrazo() != null ? t.getPrazo().toString() : "N/A");
                                    System.out.printf("Status: %s%n", t.getConcluida() != null && t.getConcluida() ? "Concluída" : "Pendente");
                                    
                                    if (t.isPrazoVencido()) {
                                        System.out.println("⚠️ PRAZO VENCIDO!");
                                    }
                                    System.out.println();
                                }
                            }
                            break;

                        case 3:
                            List<Tarefa> tarefasParaAtualizar = tarefaDao.buscarTarefas();
                            if (tarefasParaAtualizar.isEmpty()) {
                                System.out.println("Nenhuma tarefa encontrada.");
                                break;
                            }
                            
                            System.out.println("\nTarefas disponíveis:");
                            for (Tarefa t : tarefasParaAtualizar) {
                                System.out.printf("ID: %d - Título: %s - Status: %s%n", 
                                    t.getTarefaId() != null ? t.getTarefaId() : 0,
                                    t.getTitulo() != null ? t.getTitulo() : "N/A",
                                    t.getConcluida() != null && t.getConcluida() ? "Concluída" : "Pendente");
                            }
                            
                            System.out.println("\nDigite o ID da tarefa que deseja atualizar: ");
                            int idTarefa = scanner.nextInt();
                            scanner.nextLine();
                            
                            Tarefa tarefaSelecionada = null;
                            for (Tarefa t : tarefasParaAtualizar) {
                                if (t.getTarefaId() != null && t.getTarefaId() == idTarefa) {
                                    tarefaSelecionada = t;
                                    break;
                                }
                            }
                            
                            if (tarefaSelecionada != null) {
                                System.out.println("Digite o novo título da tarefa (atual: " + tarefaSelecionada.getTitulo() + "): ");
                                String novoTitulo = scanner.nextLine();
                                
                                System.out.println("Digite o novo responsável (atual: " + tarefaSelecionada.getResponsavel() + "): ");
                                String novoResponsavel = scanner.nextLine();
                                
                                System.out.println("Digite o novo prazo (yyyy-MM-dd) (atual: " + tarefaSelecionada.getPrazo() + "): ");
                                LocalDate novoPrazo = LocalDate.parse(scanner.nextLine());
                                
                                System.out.println("A tarefa está concluída? (s/n) (atual: " + 
                                    (tarefaSelecionada.getConcluida() ? "Sim" : "Não") + "): ");
                                String concluida = scanner.nextLine();
                                boolean isConcluida = concluida.toLowerCase().equals("s") || concluida.toLowerCase().equals("sim");
                                
                                Tarefa tarefaAtualizada = new Tarefa();
                                tarefaAtualizada.setTitulo(novoTitulo);
                                tarefaAtualizada.setResponsavel(novoResponsavel);
                                tarefaAtualizada.setPrazo(novoPrazo);
                                tarefaAtualizada.setConcluida(isConcluida);
                                
                                tarefaDao.atualizarTarefa(tarefaAtualizada, idTarefa);
                            } else {
                                System.out.println("Tarefa não encontrada.");
                            }
                            break;

                        case 4:
                            List<Tarefa> tarefasParaDeletar = tarefaDao.buscarTarefas();
                            if (tarefasParaDeletar.isEmpty()) {
                                System.out.println("Nenhuma tarefa encontrada.");
                                break;
                            }
                            
                            System.out.println("\nTarefas disponíveis:");
                            for (Tarefa t : tarefasParaDeletar) {
                                System.out.printf("ID: %d - Título: %s - Responsável: %s%n", 
                                    t.getTarefaId() != null ? t.getTarefaId() : 0,
                                    t.getTitulo() != null ? t.getTitulo() : "N/A",
                                    t.getResponsavel() != null ? t.getResponsavel() : "N/A");
                            }
                            
                            System.out.println("\nDigite o ID da tarefa que deseja deletar: ");
                            int idTarefaDeletar = scanner.nextInt();
                            
                            boolean tarefaEncontrada = false;
                            for (Tarefa t : tarefasParaDeletar) {
                                if (t.getTarefaId() != null && t.getTarefaId() == idTarefaDeletar) {
                                    tarefaEncontrada = true;
                                    break;
                                }
                            }
                            
                            if (tarefaEncontrada) {
                                tarefaDao.deletarTarefa(idTarefaDeletar);
                            } else {
                                System.out.println("Tarefa não encontrada.");
                            }
                            break;

                        case 5:
                            System.out.println("Voltando ao menu principal...");
                            break;

                        default:
                            System.out.println("Opção inválida...");
                            break;
                    }
                    break;

                case 3:
                    System.out.println("\n1. Exibir todo o histórico \n2. Exibir histórico por tarefa \n3. Voltar ao menu principal");
                    System.out.println("Escolha uma opção: ");
                    int opcaoHistorico = scanner.nextInt();

                    switch (opcaoHistorico) {
                        case 1:
                            List<HistoricoAlteracao> todoHistorico = historicoDao.buscarHistorico();
                            if (todoHistorico.isEmpty()) {
                                System.out.println("\nNenhum histórico de alteração encontrado");
                            } else {
                                System.out.printf("Total de alterações encontradas: %d%n%n", todoHistorico.size());
                                
                                for (HistoricoAlteracao h : todoHistorico) {
                                    System.out.printf("ID: %d%n", h.getHistoricoId() != null ? h.getHistoricoId() : 0);
                                    System.out.printf("Tarefa ID: %d%n", h.getTarefaId() != null ? h.getTarefaId() : 0);
                                    System.out.printf("Data/Hora: %s%n", h.getDataAlteracao() != null ? h.getDataAlteracao().toString() : "N/A");
                                    System.out.printf("Campo Alterado: %s%n", h.getCampoAlterado() != null ? h.getCampoAlterado() : "N/A");
                                    System.out.printf("Valor Anterior: %s%n", h.getValorAntigo() != null ? h.getValorAntigo() : "N/A");
                                    System.out.printf("Valor Novo: %s%n\n", h.getValorNovo() != null ? h.getValorNovo() : "N/A\n");
                                }
                            }
                            break;

                        case 2:
                            List<Tarefa> tarefasDisponiveis = tarefaDao.buscarTarefas();
                            if (tarefasDisponiveis.isEmpty()) {
                                System.out.println("Nenhuma tarefa encontrada.");
                                break;
                            }
                            
                            System.out.println("\nTarefas disponíveis:");
                            for (Tarefa t : tarefasDisponiveis) {
                                System.out.printf("ID: %d - Título: %s%n", 
                                    t.getTarefaId() != null ? t.getTarefaId() : 0,
                                    t.getTitulo() != null ? t.getTitulo() : "N/A");
                            }
                            
                            System.out.println("\nDigite o ID da tarefa para ver o histórico: ");
                            int tarefaIdHistorico = scanner.nextInt();
                            
                            List<HistoricoAlteracao> historicoPorTarefa = historicoDao.buscarHistoricoPorTarefa(tarefaIdHistorico);
                            if (historicoPorTarefa.isEmpty()) {
                                System.out.println("\nNenhum histórico encontrado para esta tarefa");
                            } else {
                                System.out.printf("Histórico da tarefa %d (%d alterações):%n%n", tarefaIdHistorico, historicoPorTarefa.size());
                                
                                for (HistoricoAlteracao h : historicoPorTarefa) {
                                    System.out.printf("Data/Hora: %s%n", h.getDataAlteracao() != null ? h.getDataAlteracao().toString() : "N/A");
                                    System.out.printf("Campo: %s%n", h.getCampoAlterado() != null ? h.getCampoAlterado() : "N/A");
                                    System.out.printf("De: %s => Para: %s%n\n", 
                                        h.getValorAntigo() != null ? h.getValorAntigo() : "N/A\n",
                                        h.getValorNovo() != null ? h.getValorNovo() : "N/A\n");
                                }
                            }
                            break;

                        case 3:
                            break;

                        default:
                            System.out.println("Opção inválida...");
                            break;
                    }
                    break;

                case 4:
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
