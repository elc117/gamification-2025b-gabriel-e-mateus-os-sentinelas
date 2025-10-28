package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.ufsmSistemas.tcgParadigma.Screens.FirstScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Game {

    @Override
    public void create() {
        // Caminho do banco local
        FileHandle dbFile = Gdx.files.local("database/game.db");

        // Cria a pasta "database" se não existir
        if (!dbFile.parent().exists()) dbFile.parent().mkdirs();

        try {
            // Carrega driver SQLite
            Class.forName("org.sqlite.JDBC");

            // Conecta ao banco
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.file().getAbsolutePath());

            // Teste: imprimir todos os jogadores
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("INSERT INTO carta (nome, caminhoImagem, raridade, categoria) VALUES ('Mateus', 'no', 'ultaermega', 'sim')");
            ResultSet rs = stmt.executeQuery("SELECT * FROM carta");

            System.out.println("Conteúdo da tabela player:");
            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                System.out.println("Jogador: " + id + " | " + name + " | " + score);
            }
            if (!hasRows) System.out.println("A tabela está vazia ou não existe.");

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Continua com o jogo
        setScreen(new FirstScreen());
    }
}
