package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.Booster;

public class TelaLoja extends TelaBase {

    private Booster booster1;
    private Booster booster2;

    public TelaLoja(Main game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        // Botões dos boosters
        TextButton botaoBooster1 = new TextButton("Booster 1", skin);
        TextButton botaoBooster2 = new TextButton("Booster 2", skin);

        botaoBooster1.setSize(200, 80);
        botaoBooster2.setSize(200, 80);

        float centerY = Gdx.graphics.getHeight() / 2f;
        botaoBooster1.setPosition(Gdx.graphics.getWidth() / 4f - botaoBooster1.getWidth() / 2f, centerY);
        botaoBooster2.setPosition(Gdx.graphics.getWidth() * 3 / 4f - botaoBooster2.getWidth() / 2f, centerY);

        botaoBooster1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("[TelaLoja] ===== BOTAO CLICADO =====");
                new Booster(cartas -> {
                    System.out.println("[TelaLoja] ===== CALLBACK CHAMADO =====");
                    System.out.println("[TelaLoja] Número de cartas: " + cartas.size());
                    for (int i = 0; i < cartas.size(); i++) {
                        System.out.println("[TelaLoja]   Carta " + i + ": " + cartas.get(i).getNome());
                    }

                    Gdx.app.postRunnable(() -> {
                        System.out.println("[TelaLoja] ===== ABRINDO TELA =====");
                        TelaAberturaBooster telaBooster = new TelaAberturaBooster(game, null);
                        telaBooster.setCartas(cartas);
                        game.setScreen(telaBooster);
                    });
                });
            }
        });botaoBooster1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("[TelaLoja] ===== BOTAO CLICADO =====");
                new Booster(cartas -> {
                    System.out.println("[TelaLoja] ===== CALLBACK CHAMADO =====");
                    System.out.println("[TelaLoja] Número de cartas: " + cartas.size());
                    for (int i = 0; i < cartas.size(); i++) {
                        System.out.println("[TelaLoja]   Carta " + i + ": " + cartas.get(i).getNome());
                    }

                    Gdx.app.postRunnable(() -> {
                        System.out.println("[TelaLoja] ===== ABRINDO TELA =====");
                        TelaAberturaBooster telaBooster = new TelaAberturaBooster(game, null);
                        telaBooster.setCartas(cartas);
                        game.setScreen(telaBooster);
                    });
                });
            }
        });

        botaoBooster2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("[TelaLoja] Criando booster...");
                new Booster(cartas -> {
                    System.out.println("[TelaLoja] Callback recebido com " + cartas.size() + " cartas!");
                    Gdx.app.postRunnable(() -> {
                        System.out.println("[TelaLoja] Abrindo TelaAberturaBooster...");
                        TelaAberturaBooster telaBooster = new TelaAberturaBooster(game, null);
                        telaBooster.setCartas(cartas);
                        game.setScreen(telaBooster);
                    });
                });
            }
        });

        stage.addActor(botaoBooster1);
        stage.addActor(botaoBooster2);

        // Botão para voltar à tela inicial
        TextButton botaoVoltar = new TextButton("Voltar", skin);
        botaoVoltar.setSize(150, 60);
        botaoVoltar.setPosition(20, Gdx.graphics.getHeight() - 80);
        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaInicialJogo(game));
            }
        });
        stage.addActor(botaoVoltar);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
