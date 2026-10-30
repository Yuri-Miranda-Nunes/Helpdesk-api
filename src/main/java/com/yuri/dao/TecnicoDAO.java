package com.yuri.dao;

import com.yuri.config.DatabaseConnection;
import com.yuri.model.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public List<Tecnico> listarTodos() {
        List<Tecnico> tecnicos = new ArrayList<>();
        String sql = "SELECT * FROM tecnicos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tecnico tecnico = new Tecnico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("especialidade")
                );
                tecnicos.add(tecnico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar técnicos no banco de dados.", e);
        }

        return tecnicos;
    }
}