package com.ufsmSistemas.tcgParadigma.screens.loja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.Booster;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaInicialJogo;

public class TelaLoja extends TelaBase {

    public TelaLoja(Main game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // ============================
        // Criação dos botões
        // ============================
        TextButton botaoBooster1 = criarBotao("Booster 1", Gdx.graphics.getWidth() / 4f);
        TextButton botaoBooster2 = criarBotao("Booster 2", Gdx.graphics.getWidth() * 3 / 4f);

        botaoBooster1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                abrirBooster();
            }
        });

        botaoBooster2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                abrirBooster();
            }
        });

        // ============================
        // Botão Voltar
        // ============================
        TextButton botaoVoltar = new TextButton("Voltar", skin);
        botaoVoltar.setSize(150, 60);
        botaoVoltar.setPosition(20, Gdx.graphics.getHeight() - 80);
        botaoVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TelaInicialJogo(game));
            }
        });

        // ============================
        // Adiciona tudo no Stage
        // ============================
        stage.addActor(botaoBooster1);
        stage.addActor(botaoBooster2);
        stage.addActor(botaoVoltar);
    }

    private TextButton criarBotao(String texto, float posicaoX) {
        TextButton botao = new TextButton(texto, skin);
        botao.setSize(200, 80);
        botao.setPosition(posicaoX - botao.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        return botao;
    }

    private void abrirBooster() {
        System.out.println("[TelaLoja] ===== ABRINDO BOOSTER =====");
        new Booster(cartas -> {
            System.out.println("[TelaLoja] Callback recebido com " + cartas.size() + " cartas!");

            Gdx.app.postRunnable(() -> {
                TelaAberturaBooster telaBooster = new TelaAberturaBooster(game, null);
                telaBooster.setCartas(cartas);
                game.setScreen(telaBooster);
            });
        });
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
