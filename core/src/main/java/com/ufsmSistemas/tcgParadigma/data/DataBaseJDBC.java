package com.ufsmSistemas.tcgParadigma.data;

import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseEntity;
import com.ufsmSistemas.tcgParadigma.interfaces.DataBaseInterface;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import java.sql.*;

public class DataBaseJDBC implements DataBaseInterface {
    private static final String DB_NAME = "game.db";
    private static Connection connection;

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String dbPath = "database/" + DB_NAME;
            String url = "jdbc:sqlite:" + dbPath;
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    public void insert(DataBaseEntity entity) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement pstmt = conn.prepareStatement(entity.getInsertSQL(), PreparedStatement.RETURN_GENERATED_KEYS);
        entity.setInsertParameters(pstmt);
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next() && (entity instanceof Jogador)) {
            Jogador jogador = (Jogador) entity;
            jogador.setId(rs.getInt(1));
        }

        conn.close();
    }
}
