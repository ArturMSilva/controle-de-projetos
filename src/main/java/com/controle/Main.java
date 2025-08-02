package com.controle;

import com.controle.dao.Conexao;
import com.controle.dao.ProjetoDao;
import com.controle.model.Projeto;
import com.controle.model.Tarefa;
import com.controle.ui.Menu;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o Controle de Projetos...");

        Conexao conexao = new Conexao();
        Connection conn = conexao.conectarBanco();

        Menu menu = new Menu();
        menu.menu();

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}