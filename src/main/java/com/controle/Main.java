package com.controle;

import com.controle.dao.Conexao;
import com.controle.ui.Menu;

import java.sql.Connection;

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