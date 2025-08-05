package com.controle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controle.model.HistoricoAlteracao;

public class HistoricoAlteracaoDao {
    private Connection connection;

    public HistoricoAlteracaoDao(Connection connection) {
        this.connection = connection;
    }

    public List<HistoricoAlteracao> buscarHistorico() {
        String sql = "SELECT * FROM historico_alteracoes ORDER BY data_alteracao DESC";
        List<HistoricoAlteracao> historicos = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                HistoricoAlteracao historico = new HistoricoAlteracao();
                historico.setHistoricoId(result.getInt("historico_id"));
                historico.setTarefaId(result.getInt("tarefa_id"));
                historico.setDataAlteracao(result.getTimestamp("data_alteracao").toLocalDateTime());
                historico.setCampoAlterado(result.getString("campo_alterado"));
                historico.setValorAntigo(result.getString("valor_antigo"));
                historico.setValorNovo(result.getString("valor_novo"));

                historicos.add(historico);
            }

            System.out.println("Histórico de alterações encontrado: " + historicos.size() + " registros");

        } catch (SQLException e) {
            System.out.println("Erro ao buscar histórico de alterações: " + e.getMessage());
        }

        return historicos;
    }

    public List<HistoricoAlteracao> buscarHistoricoPorTarefa(int tarefaId) {
        String sql = "SELECT * FROM historico_alteracoes WHERE tarefa_id = ? ORDER BY data_alteracao DESC";
        List<HistoricoAlteracao> historicos = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tarefaId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                HistoricoAlteracao historico = new HistoricoAlteracao();
                historico.setHistoricoId(result.getInt("historico_id"));
                historico.setTarefaId(result.getInt("tarefa_id"));
                historico.setDataAlteracao(result.getTimestamp("data_alteracao").toLocalDateTime());
                historico.setCampoAlterado(result.getString("campo_alterado"));
                historico.setValorAntigo(result.getString("valor_antigo"));
                historico.setValorNovo(result.getString("valor_novo"));

                historicos.add(historico);
            }

            System.out.println("Histórico da tarefa " + tarefaId + ": " + historicos.size() + " registros");

        } catch (SQLException e) {
            System.out.println("Erro ao buscar histórico da tarefa: " + e.getMessage());
        }

        return historicos;
    }
}
