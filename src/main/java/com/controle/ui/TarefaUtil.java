package com.controle.ui;

import com.controle.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaUtil {
    
    public static void exibirTarefa(Tarefa tarefa, boolean formatoCompleto) {
        if (formatoCompleto) {
            System.out.printf("Tarefa ID: %d%n", tarefa.getTarefaId() != null ? tarefa.getTarefaId() : 0);
            System.out.printf("Projeto ID: %d%n", tarefa.getProjetoId() != null ? tarefa.getProjetoId() : 0);
            System.out.printf("Título: %s%n", tarefa.getTitulo() != null ? tarefa.getTitulo() : "N/A");
            System.out.printf("Responsável: %s%n", tarefa.getResponsavel() != null ? tarefa.getResponsavel() : "N/A");
            System.out.printf("Prazo: %s%n", tarefa.getPrazo() != null ? tarefa.getPrazo().toString() : "N/A");
            System.out.printf("Status: %s%n", tarefa.getConcluida() != null && tarefa.getConcluida() ? "Concluída" : "Pendente");
            
            if (tarefa.isPrazoVencido()) {
                System.out.println("⚠️ PRAZO VENCIDO!");
            }
            System.out.println();
        } else {
            System.out.printf("ID: %d - Título: %s - Responsável: %s - Status: %s", 
                tarefa.getTarefaId() != null ? tarefa.getTarefaId() : 0,
                tarefa.getTitulo() != null ? tarefa.getTitulo() : "N/A",
                tarefa.getResponsavel() != null ? tarefa.getResponsavel() : "N/A",
                tarefa.getConcluida() != null && tarefa.getConcluida() ? "Concluída" : "Pendente");
            
            if (tarefa.isPrazoVencido()) {
                System.out.print(" ⚠️ PRAZO VENCIDO!");
            }
            System.out.println();
        }
    }

    public static void listarTarefas(List<Tarefa> tarefas, String titulo) {
        if (tarefas.isEmpty()) {
            System.out.println("\nNenhuma tarefa encontrada");
            return;
        }
        
        System.out.printf("Total de tarefas encontradas: %d%n%n", tarefas.size());
        for (Tarefa tarefa : tarefas) {
            exibirTarefa(tarefa, true); 
        }
    }

    public static List<Tarefa> filtrarTarefasPendentes(List<Tarefa> todasTarefas) {
        List<Tarefa> tarefasPendentes = new ArrayList<>();
        for (Tarefa t : todasTarefas) {
            if (t.getConcluida() == null || !t.getConcluida()) {
                tarefasPendentes.add(t);
            }
        }
        return tarefasPendentes;
    }

    public static Tarefa encontrarTarefaPorId(List<Tarefa> tarefas, int id) {
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getTarefaId() != null && tarefa.getTarefaId() == id) {
                return tarefa;
            }
        }
        return null;
    }
}
