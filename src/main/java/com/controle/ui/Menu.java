package com.controle.ui;

import com.controle.dao.HistoricoAlteracaoDao;
import com.controle.dao.ProjetoDao;
import com.controle.dao.TarefaDao;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private ProjetoDao projetoDao;
    private TarefaDao tarefaDao;
    private HistoricoAlteracaoDao historicoDao;
    
    private MenuProjeto menuProjeto;
    private MenuTarefa menuTarefa;
    private MenuHistorico menuHistorico;

    public Menu(Connection connection) {
        this.projetoDao = new ProjetoDao(connection);
        this.tarefaDao = new TarefaDao(connection);
        this.historicoDao = new HistoricoAlteracaoDao(connection);
        
        this.menuProjeto = new MenuProjeto(scanner, projetoDao);
        this.menuTarefa = new MenuTarefa(scanner, tarefaDao, projetoDao);
        this.menuHistorico = new MenuHistorico(scanner, historicoDao, tarefaDao);
    }

    public void menu() {
        boolean i = true;

        while (i) {
            System.out.println("\n1. Projetos \n2. Tarefas \n3. Histórico de Alterações \n4. Encerrar programa");
            System.out.println("\nEscolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    menuProjeto.menuProjeto();
                    break;

                case 2:
                    menuTarefa.menuTarefa();
                    break;

                case 3:
                    menuHistorico.menuHistorico();
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
