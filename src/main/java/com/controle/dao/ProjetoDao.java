package com.controle.dao;

import com.controle.model.Projeto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjetoDao {
    private Connection connection;

    public ProjetoDao(Connection connection) {
        this.connection = connection;
    }

    public void criarProjeto(Projeto projeto) {
        String sql = "INSERT INTO projetos (nome, descricao, data_inicio, data_fim, status) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, projeto.getNome());
            statement.setString(2, projeto.getDescricao());
            statement.setDate(3, Date.valueOf(projeto.getDataInicio()));
            statement.setDate(4, Date.valueOf(projeto.getDataFim()));
            statement.setString(5, projeto.getStatus());
            statement.executeUpdate();
            System.out.println("Projeto adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar projeto: " + e.getMessage());
        }
    }

    public List<Projeto> buscarProjetos() {
        String sql = "SELECT * FROM projetos";
        List<Projeto> projetos = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Projeto projeto = new Projeto();
                projeto.setNome(result.getString("nome"));
                projeto.setDescricao(result.getString("descricao"));
                projeto.setDataInicio(result.getDate("data_inicio").toLocalDate());
                projeto.setDataFim(result.getDate("data_fim").toLocalDate());
                projeto.setStatus(result.getString("status"));

                projetos.add(projeto);
            }

            System.out.println("Projetos encontrados: " + projetos.size());

        } catch (SQLException e) {
            System.out.println("Erro ao buscar projetos: " + e.getMessage());
        }

        return projetos;
    }

    public Projeto atualizaProjeto(Projeto projeto, int id) {
        String sql = "UPDATE projetos SET nome = ?, descricao = ?, data_inicio = ?, data_fim = ?, status = ? WHERE projeto_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, projeto.getNome());
            statement.setString(2, projeto.getDescricao());
            statement.setDate(3, Date.valueOf(projeto.getDataInicio()));
            statement.setDate(4, Date.valueOf(projeto.getDataFim()));
            statement.setString(5, projeto.getStatus());
            statement.setInt(6, id);
            statement.executeUpdate();
            System.out.println("Projeto atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar projeto: " + e.getMessage());
        }

        return projeto;
    }

    public void deletarProjeto(int id) {
        String sql = "DELETE FROM projetos WHERE projeto_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Projeto deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar projeto: " + e.getMessage());
        }
    }

    public Map<String, List<String>> listarProjetoComTarefasRelacionadas() {
        String sql = "SELECT p.nome, t.titulo FROM projetos p LEFT JOIN tarefas t ON p.projeto_id = t.projeto_id ORDER BY p.nome";
        Map<String, List<String>> projetosComTarefas = new HashMap<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String nomeProjeto = result.getString("nome");
                String tituloTarefa = result.getString("titulo");

                if (!projetosComTarefas.containsKey(nomeProjeto)) {
                    projetosComTarefas.put(nomeProjeto, new ArrayList<>());
                }

                if (tituloTarefa != null) {
                    projetosComTarefas.get(nomeProjeto).add(tituloTarefa);
                } else {
                    if (projetosComTarefas.get(nomeProjeto).isEmpty()) {
                        projetosComTarefas.get(nomeProjeto).add("Nenhuma tarefa relacionada");
                    }
                }
            }

            System.out.println("Projetos com tarefas relacionadas encontrados: " + projetosComTarefas.size());

        } catch (SQLException e) {
            System.out.println("Erro ao buscar projetos com tarefas relacionadas: " + e.getMessage());
        }

        return projetosComTarefas;
    }
    }
