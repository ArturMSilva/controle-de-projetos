package com.controle.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controle.model.Tarefa;

public class TarefaDao {
    private Connection connection;

    public TarefaDao(Connection connection) {
        this.connection = connection;
    }

    public void criarTarefa(Tarefa tarefa) {
        String sql = "CALL adicionar_tarefa(?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tarefa.getProjetoId());
            statement.setString(2, tarefa.getTitulo());
            statement.setString(3, tarefa.getResponsavel());
            statement.setDate(4, Date.valueOf(tarefa.getPrazo()));
            statement.executeUpdate();

            System.out.println("Tarefa adicionada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar tarefa: " + e.getMessage());
        }
    }

    public List<Tarefa> buscarTarefas() {
        String sql = "SELECT * FROM tarefas";
        List<Tarefa> tarefas = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setProjetoId(result.getInt("projeto_id"));
                tarefa.setTitulo(result.getString("titulo"));
                tarefa.setResponsavel(result.getString("responsavel"));
                tarefa.setPrazo(result.getDate("prazo").toLocalDate());
                tarefa.setConcluida(result.getBoolean("concluida"));

                tarefas.add(tarefa);
            }

            System.out.println("Tarefas encontradas: " + tarefas.size());

        } catch (SQLException e) {
            System.out.println("Erro ao buscar tarefas: " + e.getMessage());
        }

        return tarefas;
    }

    public void atualizarTarefa(Tarefa tarefa, int id) {
        String sql = "UPDATE tarefas SET titulo = ?, responsavel = ?, prazo = ?, concluida = ? WHERE tarefa_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getResponsavel());
            statement.setDate(3, Date.valueOf(tarefa.getPrazo()));
            statement.setBoolean(4, tarefa.getConcluida());
            statement.setInt(5, id);
            statement.executeUpdate();

            System.out.println("Tarefa atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }
}
