package com.controle;

import com.controle.dao.Conexao;
import com.controle.dao.ProjetoDao;
import com.controle.model.Projeto;
import com.controle.model.Tarefa;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o Controle de Projetos...");

        Conexao conexao = new Conexao();
        Connection conn = conexao.conectarBanco();
        ProjetoDao projetoDao = new ProjetoDao(conn);

        LocalDate hoje = LocalDate.now();
        Projeto projeto = new Projeto("Rafael", "Teste de Atualização", hoje, hoje.plusDays(30));

        projetoDao.criarProjeto(projeto);

        projetoDao.deletarProjeto(7);

        List<Projeto> projetos = projetoDao.buscarProjetos();

        for (Projeto p : projetos) {
            System.out.println(p.toString());
        }

        projetoDao.atualizaProjeto(projeto, 7);

        Tarefa tarefa = new Tarefa(2, "Tarefa de Teste", "Mateo", hoje.plusDays(5));
  
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}