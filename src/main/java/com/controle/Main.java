package com.controle;

import com.controle.dao.Conexao;
import com.controle.dao.ProjetoDao;
import com.controle.model.Projeto;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o Controle de Projetos...");
        
        // Testando conexão com o banco de dados
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectarBanco();

        LocalDate hoje = LocalDate.now();
        Projeto projeto = new Projeto("Rafael", "Descricao", hoje, hoje.plusDays(30));

        ProjetoDao projetoDao = new ProjetoDao(conn);

        projetoDao.criarProjeto(projeto);

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}