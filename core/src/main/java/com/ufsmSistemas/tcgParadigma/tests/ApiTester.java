package com.ufsmSistemas.tcgParadigma.tests;

import com.badlogic.gdx.Gdx;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.models.Jogador;

public class ApiTester {

    private static boolean testeConcluido = false;

    public static void executarTeste() {
        if (testeConcluido) {
            Gdx.app.log("API_TEST", "Teste já foi executado!");
            return;
        }

        testeConcluido = true;

        Gdx.app.log("API_TEST", "========================================");
        Gdx.app.log("API_TEST", "INICIANDO TESTE DA API");
        Gdx.app.log("API_TEST", "========================================");

        // Criar jogador de teste
        final Jogador jogador = new Jogador("testador", "123");
        jogador.setNome("Testador_" + System.currentTimeMillis());
        jogador.setSenha("teste123");
        jogador.setPontos(0);
        jogador.setQuizesRespondidos(0);
        jogador.setQuantidadeBoosterAbertos(0);

        Gdx.app.log("API_TEST", "1. Inserindo jogador: " + jogador.getNome());

        // INSERT
        DataBaseAPI.insert(jogador);

        // Aguardar e fazer UPDATE
        agendarUpdate(jogador, 3000);
    }

    private static void agendarUpdate(final Jogador jogador, long delay) {
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (jogador.getId() > 0) {
                            Gdx.app.log("API_TEST", "2. INSERT OK! ID: " + jogador.getId());
                            Gdx.app.log("API_TEST", "3. Atualizando jogador...");

                            jogador.setPontos(500);
                            jogador.setQuizesRespondidos(10);
                            jogador.setQuantidadeBoosterAbertos(2);

                            DataBaseAPI.update(jogador);

                            // Aguardar e fazer GET
                            agendarGet(jogador, 3000);
                        } else {
                            Gdx.app.error("API_TEST", "ERRO: ID não foi definido no INSERT!");
                        }
                    }
                });
            }
        }, delay);
    }

    private static void agendarGet(final Jogador jogador, long delay) {
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.log("API_TEST", "4. UPDATE OK! Buscando jogador...");
                        // Aguardar resultado
                        agendarResultado(jogador, 2000);
                    }
                });
            }
        }, delay);
    }

    private static void agendarResultado(final Jogador jogador, long delay) {
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.log("API_TEST", "========================================");
                        Gdx.app.log("API_TEST", "RESULTADO FINAL:");
                        Gdx.app.log("API_TEST", "  ID: " + jogador.getId());
                        Gdx.app.log("API_TEST", "  Nome: " + jogador.getNome());
                        Gdx.app.log("API_TEST", "  Pontos: " + jogador.getPontos());
                        Gdx.app.log("API_TEST", "  Quizes: " + jogador.getQuizesRespondidos());
                        Gdx.app.log("API_TEST", "  Boosters: " + jogador.getQuantidadeBoosterAbertos());
                        Gdx.app.log("API_TEST", "========================================");
                        Gdx.app.log("API_TEST", "✅ TESTE CONCLUÍDO COM SUCESSO!");
                        Gdx.app.log("API_TEST", "========================================");
                    }
                });
            }
        }, delay);
    }
}
