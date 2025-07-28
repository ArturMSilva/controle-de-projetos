package com.controle;

import com.controle.dao.Conexao;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o Controle de Projetos...");
        
        // Testando conexão com o banco de dados
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectarBanco();
        
        // Fechando a conexão se ela foi estabelecida
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}