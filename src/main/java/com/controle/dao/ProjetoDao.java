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
}
