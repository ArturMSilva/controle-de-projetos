package com.controle.ui;

import com.controle.dao.ProjetoDao;
import com.controle.model.Projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuProjeto {
    private Scanner scanner;
    private ProjetoDao projetoDao;

    public MenuProjeto(Scanner scanner, ProjetoDao projetoDao) {
        this.scanner = scanner;
        this.projetoDao = projetoDao;
    }

    public void menuProjeto() {
        System.out.println(
                "\n1. Criar projeto \n2. Listar projetos \n3. Listar projetos com tarefas \n4. Estatísticas por status \n5. Calcular progresso \n6. Atualizar projeto \n7. Deletar projeto");
        System.out.println("Escolha uma opção: ");
        int opcaoProjeto = scanner.nextInt();

        scanner.nextLine();
        switch (opcaoProjeto) {
            case 1:
                criarProjeto();
                break;
            case 2:
                listarProjetos();
                break;
            case 3:
                listarProjetosComTarefas();
                break;
            case 4:
                exibirEstatisticasPorStatus();
                break;
            case 5:
                calcularProgresso();
                break;
            case 6:
                atualizarProjeto();
                break;
            case 7:
                deletarProjeto();
                break;
        }
    }

    private void criarProjeto() {
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
    }

    private void listarProjetos() {
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
    }

    private void listarProjetosComTarefas() {
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
    }

    private void exibirEstatisticasPorStatus() {
        Map<String, Integer> estatisticas = projetoDao.obterEstatisticasPorStatus();
        if (estatisticas.isEmpty()) {
            System.out.println("Nenhum projeto encontrado");
        } else {
            for (Map.Entry<String, Integer> entry : estatisticas.entrySet()) {
                System.out.println("Status: " + entry.getKey() + " - Quantidade: " + entry.getValue());
            }
        }
    }

    private void calcularProgresso() {
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
    }

    private void atualizarProjeto() {
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
    }

    private void deletarProjeto() {
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
}
