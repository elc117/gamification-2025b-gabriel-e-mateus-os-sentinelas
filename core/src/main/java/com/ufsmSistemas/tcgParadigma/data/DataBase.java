package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntity;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import java.sql.*;

public class DataBase {
    private static final String DB_NAME = "game.db";
    private static Connection connection;

    // Connection methods
    public static Connection getConnection() {
        if(connection == null) {
            try {
                String dbPath = "database/" + DB_NAME;
                String url = "jdbc:sqlite:" + dbPath;

                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insert(DataBaseEntity entity) throws SQLException{
        Connection conn = getConnection();

        PreparedStatement pstmt = conn.prepareStatement(entity.getInsertSQL(), PreparedStatement.RETURN_GENERATED_KEYS);
        entity.setInsertParameters(pstmt);
        pstmt.executeUpdate();

        // Set the user id to the object
        ResultSet rs = pstmt.getGeneratedKeys();
        if(rs.next() && entity instanceof Jogador) {
            Jogador jogador = (Jogador) entity;
            jogador.setId(rs.getInt(1));
        }

        closeConnection();
    }
}
