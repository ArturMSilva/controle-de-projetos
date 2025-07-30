package com.controle.dao;

import com.controle.model.Projeto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjetoDao {
    private Connection connection;

    public ProjetoDao(Connection connection){
        this.connection = connection;
    }

    public void criarProjeto(Projeto projeto){
        String sql = "INSERT INTO projetos (nome, descricao, dataInicio, dataFim, status) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, projeto.getNome());
            statement.setString(2, projeto.getDescricao());
            statement.setDate(3, Date.valueOf(projeto.getDataInicio()));
            statement.setDate(4, Date.valueOf(projeto.getDataFim()));
            statement.setString(5, projeto.getStatus());
            statement.executeUpdate();
            System.out.println("Projeto adicionado com sucesso!");
        }catch (SQLException e){
            System.out.println("Erro ao adicionar projeto: " + e.getMessage());
        }
    }
}
