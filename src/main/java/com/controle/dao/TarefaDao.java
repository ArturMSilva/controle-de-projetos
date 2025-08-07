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
                tarefa.setTarefaId(result.getInt("tarefa_id"));
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

    public int contarTarefasPorProjeto(int projetoId) {
        String sql = "SELECT COUNT(*) as quantidade_tarefas FROM tarefas WHERE projeto_id = ?";
        int quantidadeTarefas = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, projetoId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                quantidadeTarefas = result.getInt("quantidade_tarefas");
            }

            System.out.println("Quantidade de tarefas do projeto ID " + projetoId + ": " + quantidadeTarefas);

        } catch (SQLException e) {
            System.out.println("Erro ao contar tarefas do projeto: " + e.getMessage());
        }

        return quantidadeTarefas;
    }

    public void deletarTarefa(int tarefaId) {
        String sql = "DELETE FROM tarefas WHERE tarefa_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tarefaId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tarefa deletada com sucesso!");
            } else {
                System.out.println("Nenhuma tarefa encontrada com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar tarefa: " + e.getMessage());
        }
    }

    public void concluirTarefa(int tarefaId) {
        String sql = "UPDATE tarefas SET concluida = true WHERE tarefa_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tarefaId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tarefa marcada como concluída com sucesso!");
            } else {
                System.out.println("Nenhuma tarefa encontrada com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao concluir tarefa: " + e.getMessage());
        }
    }
}
