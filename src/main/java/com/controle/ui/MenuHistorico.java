package com.controle.ui;

import com.controle.dao.HistoricoAlteracaoDao;
import com.controle.dao.TarefaDao;
import com.controle.model.HistoricoAlteracao;
import com.controle.model.Tarefa;

import java.util.List;
import java.util.Scanner;

public class MenuHistorico {
    private Scanner scanner;
    private HistoricoAlteracaoDao historicoDao;
    private TarefaDao tarefaDao;

    public MenuHistorico(Scanner scanner, HistoricoAlteracaoDao historicoDao, TarefaDao tarefaDao) {
        this.scanner = scanner;
        this.historicoDao = historicoDao;
        this.tarefaDao = tarefaDao;
    }

    public void menuHistorico() {
        System.out.println("\n1. Exibir todo o histórico \n2. Exibir histórico por tarefa \n3. Voltar ao menu principal");
        System.out.println("Escolha uma opção: ");
        int opcaoHistorico = scanner.nextInt();

        switch (opcaoHistorico) {
            case 1:
                exibirTodoHistorico();
                break;
            case 2:
                exibirHistoricoPorTarefa();
                break;
            case 3:
                break;
            default:
                System.out.println("Opção inválida...");
                break;
        }
    }

    private void exibirTodoHistorico() {
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
    }

    private void exibirHistoricoPorTarefa() {
        List<Tarefa> tarefasDisponiveis = tarefaDao.buscarTarefas();
        if (tarefasDisponiveis.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
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
    }
}
