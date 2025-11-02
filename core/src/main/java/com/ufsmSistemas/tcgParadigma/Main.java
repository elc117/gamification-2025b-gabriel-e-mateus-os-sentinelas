package com.ufsmSistemas.tcgParadigma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.models.Booster;
import com.ufsmSistemas.tcgParadigma.models.Carta;
import com.ufsmSistemas.tcgParadigma.models.Jogador;
import com.ufsmSistemas.tcgParadigma.screens.TelaMenu;

public class Main extends Game {
    @Override
    public void create() {
        Gdx.app.log("Main", "🚀 Iniciando aplicação...");

        // Criar objeto jogador e definir nome/senha
        final Jogador jogador = new Jogador();
        jogador.setNome("apiOnlineTester");
        jogador.setSenha("1234");

            Booster booster = new Booster(cartas -> {
                Gdx.app.log("Booster", "Booster completo! Cartas carregadas:");
                for (Carta c : cartas) {
                    Gdx.app.log("Booster", c.getNome());
                }

                System.out.println(cartas);
            });

        // 🔹 Teste com banco online: inserir jogador na API
        Gdx.app.log("Main", "📤 Inserindo jogador no banco...");
        DataBaseAPI.insert(jogador);

        // 🔹 Caso queira testar sem banco, comentar as linhas acima e usar:
        // jogador.setId(1); // simula ID do jogador
        // jogador.setPontos(50);
        // jogador.setQuizesRespondidos(3);
        // Usar Timer do LibGDX (compatível com GWT) para esperar a inserção
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (jogador.getId() > 0) { // Verifica se a API setou o ID
                    Gdx.app.log("Main", "✅ Jogador inserido com ID: " + jogador.getId());

                    // 🔹 Atualizar dados do jogador (pontos, quizzes)
                    jogador.setPontos(50);
                    jogador.setQuizesRespondidos(3);

                    Gdx.app.log("Main", "📤 Atualizando dados do jogador...");
                    DataBaseAPI.update(jogador);

                    // Aguardar update completar antes de abrir o menu
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            Gdx.app.log("Main", "✅ Update concluído! Abrindo menu...");
                            setScreen(new TelaMenu(Main.this));
                        }
                    }, 2f); // Espera 2 segundos
                } else {
                    // 🔹 Erro caso ID não seja setado pela API
                    Gdx.app.error("Main", "❌ ERRO: ID não foi setado! Verifique a API.");
                    // Mesmo com erro, abrir o menu
                    setScreen(new TelaMenu(Main.this));
                }
            }
        }, 3f); // Espera 3 segundos antes de rodar o Timer


    }
}
