package com.yuri.dao;

import com.yuri.config.DatabaseConnection;
import com.yuri.model.Chamado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDAO {

    public List<Chamado> listarTodos() {
        List<Chamado> chamados = new ArrayList<>();
        String sql = "SELECT * FROM chamados ORDER BY data_abertura DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Pega o tecnico_id. Se for null no banco, o JDBC retorna 0 para getInt.
                // Para lidar com null corretamente:
                int tecId = rs.getInt("tecnico_id");
                Integer tecnicoId = rs.wasNull() ? null : tecId;

                Chamado chamado = new Chamado(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("prioridade"),
                        rs.getString("status"),
                        rs.getTimestamp("data_abertura"),
                        rs.getTimestamp("data_fechamento"),
                        rs.getInt("cliente_id"),
                        tecnicoId
                );
                chamados.add(chamado);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar chamados.", e);
        }
        return chamados;
    }

    public void salvar(Chamado chamado) {
        // A data_abertura é gerada automaticamente pelo MySQL e o status padrão é ABERTO
        String sql = "INSERT INTO chamados (titulo, descricao, prioridade, cliente_id, tecnico_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chamado.getTitulo());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getPrioridade() != null ? chamado.getPrioridade() : "MEDIA");
            stmt.setInt(4, chamado.getClienteId());

            if (chamado.getTecnicoId() != null) {
                stmt.setInt(5, chamado.getTecnicoId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar chamado.", e);
        }
    }
    public void atualizar(Chamado chamado) {
        String sql = "UPDATE chamados SET titulo=?, descricao=?, prioridade=?, status=?, cliente_id=?, tecnico_id=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chamado.getTitulo());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getPrioridade());
            stmt.setString(4, chamado.getStatus());
            stmt.setInt(5, chamado.getClienteId());

            if (chamado.getTecnicoId() != null) {
                stmt.setInt(6, chamado.getTecnicoId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, chamado.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar chamado.", e);
        }
    }
    public void deletar(int id) {
        String sql = "DELETE FROM chamados WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar chamado.", e);
        }
    }
}