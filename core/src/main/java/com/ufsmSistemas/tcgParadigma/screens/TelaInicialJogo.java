package com.ufsmSistemas.tcgParadigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.quiz.Pergunta;
import com.ufsmSistemas.tcgParadigma.quiz.Quiz;

import java.io.FileNotFoundException;

public class TelaInicialJogo extends TelaBase {

    public TelaInicialJogo(Main game) {
        super(game);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Carrega o skin a partir da pasta assets
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Cria o botão usando o skin
        TextButton botaoBooster = new TextButton("Abrir Booster", skin);
        TextButton botaoQuiz = new TextButton("Abrir Quiz", skin);

        botaoBooster.setSize(200, 60);
        botaoQuiz.setSize(200, 60);

        float y = Gdx.graphics.getHeight() / 2f - botaoBooster.getHeight() / 2f; // vertical central
        botaoBooster.setPosition(50, y); // 50 px da esquerda
        botaoQuiz.setPosition(Gdx.graphics.getWidth() - 50 - botaoQuiz.getWidth(), y); // 50 px da direita


        botaoBooster.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Abrindo Booster!");
            }
        });

        botaoQuiz.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Abrindo Quiz!");
                try {
                    Quiz quiz = new Quiz("Matemática", "difícil");
                    System.out.println("Tema: " + quiz.getTema());
                    System.out.println("Dificuldade: " + quiz.getNivelDificuldade());
                    System.out.println("Imagem Host: " + quiz.getImagemHost());
                    System.out.println("Número de perguntas: " + quiz.getPerguntaList().size());

                    // Mostrar perguntas
                    for (int i = 0; i < quiz.getPerguntaList().size(); i++) {
                        Pergunta p = quiz.getPerguntaList().get(i);
                        System.out.println((i+1) + ". " + p.getEnunciado());
                        System.out.println("  Resposta certa: " + p.getRespostaCerta());
                        System.out.println("  Erradas: " + p.getRespostaErrada());
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        stage.addActor(botaoBooster);
        stage.addActor(botaoQuiz);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
