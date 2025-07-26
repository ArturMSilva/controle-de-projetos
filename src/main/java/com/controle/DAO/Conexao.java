package com.controle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final String url = "jdbc:postgresql://localhost:5432/ControleProjetos";
    private final String usuario = "postgres";
    private final String senha = "Senhapost";

    public Connection conectarBanco(){
        try {
            Connection connection = DriverManager.getConnection(url, usuario, senha);
                if (connection != null){
                    System.out.println("Banco de dado conectado!");
                    return connection;
                }
        }catch (SQLException e){
            System.out.println("Falha ao conectar com banco de dados: " + e.getMessage());
        }

        return null;
    }
}
