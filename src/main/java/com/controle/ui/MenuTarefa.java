package com.controle.ui;

import com.controle.dao.ProjetoDao;
import com.controle.dao.TarefaDao;
import com.controle.model.Projeto;
import com.controle.model.Tarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuTarefa {
    private Scanner scanner;
    private TarefaDao tarefaDao;
    private ProjetoDao projetoDao;

    public MenuTarefa(Scanner scanner, TarefaDao tarefaDao, ProjetoDao projetoDao) {
        this.scanner = scanner;
        this.tarefaDao = tarefaDao;
        this.projetoDao = projetoDao;
    }

    public void menuTarefa() {
        System.out.println("\n1. Criar tarefa \n2. Listar tarefas \n3. Atualizar tarefa \n4. Concluir tarefa \n5. Deletar tarefa \n6. Voltar ao menu principal");
        System.out.println("Escolha uma opção: ");
        int opcaoTarefa = scanner.nextInt();

        scanner.nextLine();
        switch (opcaoTarefa) {
            case 1:
                criarTarefa();
                break;
            case 2:
                listarTarefas();
                break;
            case 3:
                atualizarTarefa();
                break;
            case 4:
                concluirTarefa();
                break;
            case 5:
                deletarTarefa();
                break;
            case 6:
                // Voltar ao menu principal - não fazer nada, o loop continuará
                break;
            default:
                System.out.println("Opção inválida...");
                break;
        }
    }

    private void criarTarefa() {
        List<Projeto> projetosDisponiveis = projetoDao.buscarProjetos();
        if (projetosDisponiveis.isEmpty()) {
            System.out.println("Nenhum projeto encontrado. É necessário criar um projeto primeiro.");
            return;
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
    }

    private void listarTarefas() {
        List<Tarefa> tarefas = tarefaDao.buscarTarefas();
        TarefaUtil.listarTarefas(tarefas, "Todas as tarefas");
    }

    private void atualizarTarefa() {
        List<Tarefa> tarefasParaAtualizar = tarefaDao.buscarTarefas();
        if (tarefasParaAtualizar.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        
        System.out.println("\nTarefas disponíveis:");
        for (Tarefa t : tarefasParaAtualizar) {
            TarefaUtil.exibirTarefa(t, false);
        }
        
        System.out.println("\nDigite o ID da tarefa que deseja atualizar: ");
        int idTarefa = scanner.nextInt();
        scanner.nextLine();
        
        Tarefa tarefaSelecionada = TarefaUtil.encontrarTarefaPorId(tarefasParaAtualizar, idTarefa);
        
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
    }

    private void concluirTarefa() {
        List<Tarefa> tarefasParaConcluir = tarefaDao.buscarTarefas();
        if (tarefasParaConcluir.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        
        List<Tarefa> tarefasPendentes = TarefaUtil.filtrarTarefasPendentes(tarefasParaConcluir);
        
        if (tarefasPendentes.isEmpty()) {
            System.out.println("Todas as tarefas já estão concluídas!");
            return;
        }
        
        System.out.println("\nTarefas pendentes:");
        for (Tarefa t : tarefasPendentes) {
            TarefaUtil.exibirTarefa(t, false); 
        }
        
        System.out.println("\nDigite o ID da tarefa que deseja concluir: ");
        int idTarefaConcluir = scanner.nextInt();
        
        Tarefa tarefaEncontrada = TarefaUtil.encontrarTarefaPorId(tarefasPendentes, idTarefaConcluir);
        
        if (tarefaEncontrada != null) {
            tarefaDao.concluirTarefa(idTarefaConcluir);
        } else {
            System.out.println("Tarefa não encontrada ou já está concluída.");
        }
    }

    private void deletarTarefa() {
        List<Tarefa> tarefasParaDeletar = tarefaDao.buscarTarefas();
        if (tarefasParaDeletar.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        
        System.out.println("\nTarefas disponíveis:");
        for (Tarefa t : tarefasParaDeletar) {
            TarefaUtil.exibirTarefa(t, false); // Formato resumido
        }
        
        System.out.println("\nDigite o ID da tarefa que deseja deletar: ");
        int idTarefaDeletar = scanner.nextInt();
        
        Tarefa tarefaParaDeletar = TarefaUtil.encontrarTarefaPorId(tarefasParaDeletar, idTarefaDeletar);
        
        if (tarefaParaDeletar != null) {
            tarefaDao.deletarTarefa(idTarefaDeletar);
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }
}
