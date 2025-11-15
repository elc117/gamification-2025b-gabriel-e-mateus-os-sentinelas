package com.ufsmSistemas.tcgParadigma.screens.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ufsmSistemas.tcgParadigma.Main;
import com.ufsmSistemas.tcgParadigma.models.quiz.Pergunta;
import com.ufsmSistemas.tcgParadigma.models.quiz.Quiz;
import com.ufsmSistemas.tcgParadigma.screens.TelaBase;
import com.ufsmSistemas.tcgParadigma.screens.TelaInicialJogo;

import java.io.FileNotFoundException;

public class TelaQuiz extends TelaBase {

    private Quiz quiz;
    private Stage stage;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Skin skin;
    private BitmapFont font;
    private BitmapFont smallFont;

    // Sprites
    private Texture playerSprite;
    private Texture opponentSprite;

    // Game State
    private int currentQuestionIndex = 0;
    private int playerHP;
    private int opponentHP;

    private float playerShakeTimer = 0;
    private float opponentShakeTimer = 0;

    // UI Elements
    private Label questionLabel;
    private Label questionCountLabel;
    private TextButton[] answerButtons;
    private TextButton responderButton;
    private TextButton desistirButton;
    private Label feedbackLabel;

    private String selectedAnswer = null;
    private boolean isAnswered = false;
    private boolean gameOver = false;

    private float feedbackTimer = 0;

    public TelaQuiz(Main game, String base, String nivel) throws FileNotFoundException {
        super(game);
        quiz = new Quiz(base, nivel);

        // Pegar HP inicial do quiz
        playerHP = 100;
        opponentHP = 100;


    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Carregar recursos
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        font = new BitmapFont();
        font.getData().setScale(1.5f);

        smallFont = new BitmapFont();
        smallFont.getData().setScale(1.2f);

        // Carregar sprites
        try {
            playerSprite = new Texture(Gdx.files.internal("player/player.png"));
        } catch (Exception e) {
            playerSprite = createDefaultTexture();
        }

        try {
            // Usar a imagem do host do quiz
            opponentSprite = new Texture(Gdx.files.internal(quiz.getImagemHost()));
        } catch (Exception e) {
            opponentSprite = createDefaultTexture();
        }

        createUI();
        loadQuestion();
    }

    private Texture createDefaultTexture() {
        // Criar uma textura padrão caso não encontre o arquivo
        return new Texture(Gdx.files.internal("badlogic.jpg")); // Use qualquer textura padrão
    }

    private void createUI() {
        // Botão Desistir no canto superior direito
        desistirButton = new TextButton("DESISTIR", skin);
        desistirButton.setPosition(Gdx.graphics.getWidth() - 500, Gdx.graphics.getHeight() - 70);
        desistirButton.setSize(150, 50);
        desistirButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (Main.musicaFundo != null) {
                    Main.musicaFundo.stop();
                    Main.musicaFundo.dispose();
                }

                Main.musicaFundo = Gdx.audio.newMusic(Gdx.files.internal("Audio/priscilaViolao.ogg"));
                Main.musicaFundo.setLooping(true);
                Main.musicaFundo.setVolume(1f);

                Main.musicaFundo.play();

                game.setScreen(new TelaInicialJogo(game));
            }
        });
        stage.addActor(desistirButton);

        // Container de pergunta e respostas (caixa de diálogo menor)
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.bottom().left();
        mainTable.padLeft(10).padBottom(10);

        Table dialogBox = new Table();
        dialogBox.setBackground(skin.newDrawable("white", new Color(1, 1, 1, 0.95f)));
        dialogBox.pad(2);

        // Contador de questões
        questionCountLabel = new Label("", skin);
        questionCountLabel.setAlignment(Align.left);
        Label.LabelStyle labelStyle = new Label.LabelStyle(questionCountLabel.getStyle());
        labelStyle.fontColor = Color.BLUE;
        questionCountLabel.setStyle(labelStyle);
        dialogBox.add(questionCountLabel).left().padBottom(8).row();

        // Label da pergunta
        questionLabel = new Label("", skin);
        questionLabel.setWrap(true);
        questionLabel.setAlignment(Align.left);
        dialogBox.add(questionLabel).width(550).padBottom(12).row();

        // Grid de respostas (2x2 compacto)
        Table answersTable = new Table();
        answerButtons = new TextButton[4];

        for (int i = 0; i < 4; i++) {
            final int index = i;
            answerButtons[i] = new TextButton("", skin);
            answerButtons[i].getLabel().setWrap(true);
            answerButtons[i].getLabel().setAlignment(Align.center);
            answerButtons[i].pad(8);

            answerButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isAnswered && !gameOver) {
                        selectAnswer(index);
                    }
                }
            });

            if (i % 2 == 0 && i > 0) {
                answersTable.row();
            }
            answersTable.add(answerButtons[i]).width(270).height(65).pad(4);
        }

        dialogBox.add(answersTable).padBottom(12).row();

        // Botão Responder
        responderButton = new TextButton("RESPONDER", skin);
        responderButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleAnswer();
            }
        });

        dialogBox.add(responderButton).width(250).height(50).padBottom(8).row();

        // Label de feedback
        feedbackLabel = new Label("", skin);
        feedbackLabel.setAlignment(Align.center);
        Label.LabelStyle feedbackStyle = new Label.LabelStyle(feedbackLabel.getStyle());
        feedbackStyle.fontColor = Color.YELLOW;
        feedbackLabel.setStyle(feedbackStyle);
        dialogBox.add(feedbackLabel).padTop(5);

        mainTable.add(dialogBox).width(600);
        stage.addActor(mainTable);
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= quiz.getPerguntaList().size()) {
            endGame();
            return;
        }

        Pergunta p = quiz.getPerguntaList().get(currentQuestionIndex);

        questionCountLabel.setText("Questão " + (currentQuestionIndex + 1) + "/" + quiz.getPerguntaList().size());
        questionLabel.setText(p.getEnunciado());

        // Embaralhar respostas
        Array<String> allAnswers = new Array<>();
        allAnswers.add(p.getRespostaCerta());
        for (String wrong : p.getRespostaErrada()) {
            allAnswers.add(wrong);
        }
        allAnswers.shuffle();

        // Preencher botões
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(allAnswers.get(i));
            answerButtons[i].setColor(Color.LIGHT_GRAY);
            answerButtons[i].setDisabled(false);
        }

        selectedAnswer = null;
        isAnswered = false;
        feedbackLabel.setText("");
        responderButton.setDisabled(false);
        desistirButton.setDisabled(false);
    }

    private void selectAnswer(int index) {
        selectedAnswer = answerButtons[index].getText().toString();

        // Reset cores
        for (TextButton btn : answerButtons) {
            btn.setColor(Color.LIGHT_GRAY);
        }

        // Destacar selecionada
        answerButtons[index].setColor(Color.YELLOW);
    }

    private void handleAnswer() {
        if (selectedAnswer == null || isAnswered || gameOver) return;

        isAnswered = true;
        responderButton.setDisabled(true);
        desistirButton.setDisabled(true);

        Pergunta p = quiz.getPerguntaList().get(currentQuestionIndex);

        if (selectedAnswer.equals(p.getRespostaCerta())) {
            feedbackLabel.setText("ACERTOU! 🎯");
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.GREEN;
            feedbackLabel.setStyle(style);

            opponentHP = Math.max(0, opponentHP - 15);
            opponentShakeTimer = 0.5f;
        } else {
            feedbackLabel.setText("ERROU! 💥 Resposta: " + p.getRespostaCerta());
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.RED;
            feedbackLabel.setStyle(style);

            playerHP = Math.max(0, playerHP - 20);
            playerShakeTimer = 0.5f;
        }

        feedbackTimer = 2.5f; // Mostrar feedback por 2.5 segundos
    }

    private void nextQuestion() {
        currentQuestionIndex++;

        if (opponentHP <= 0 || playerHP <= 0 || currentQuestionIndex >= quiz.getPerguntaList().size()) {
            endGame();
        } else {
            loadQuestion();
        }
    }

    private void endGame() {
        gameOver = true;
        responderButton.setDisabled(true);
        desistirButton.setDisabled(true);

        String resultado;
        int pontosGanhos = 0;

        if (playerHP <= 0) {
            resultado = "DERROTA! 😢\nSeu HP chegou a zero!";
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.RED;
            feedbackLabel.setStyle(style);
        } else if (opponentHP <= 0) {
            resultado = "VITÓRIA! 🏆\nVocê derrotou o oponente!";
            pontosGanhos = quiz.getPontosGanhos();
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.GREEN;
            feedbackLabel.setStyle(style);
        } else if (playerHP > opponentHP) {
            resultado = "VITÓRIA! 🏆\nVocê tem mais HP!";
            pontosGanhos = quiz.getPontosGanhos();
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.GREEN;
            feedbackLabel.setStyle(style);
        } else if (opponentHP > playerHP) {
            resultado = "DERROTA! 😢\nO oponente tem mais HP!";
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.RED;
            feedbackLabel.setStyle(style);
        } else {
            resultado = "EMPATE! 🤝";
            pontosGanhos = quiz.getPontosGanhos() / 2;
            Label.LabelStyle style = new Label.LabelStyle(feedbackLabel.getStyle());
            style.fontColor = Color.YELLOW;
            feedbackLabel.setStyle(style);
        }

        feedbackLabel.setText(resultado);

        questionLabel.setText("Fim do Quiz!\n\n" +
            "Seu HP: " + playerHP + " / " + 100 + "\n" +
            "HP Oponente: " + opponentHP + " / " + 100 + "\n\n" +
            "Pontos ganhos: " + pontosGanhos);

        questionCountLabel.setText("Tema: " + quiz.getTema() + " - " + quiz.getNivelDificuldade());

        // Esconder botões de resposta
        for (TextButton btn : answerButtons) {
            btn.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atualizar timers
        if (playerShakeTimer > 0) playerShakeTimer -= delta;
        if (opponentShakeTimer > 0) opponentShakeTimer -= delta;

        if (feedbackTimer > 0) {
            feedbackTimer -= delta;
            if (feedbackTimer <= 0 && !gameOver) {
                nextQuestion();
            }
        }

        // Desenhar fundo de batalha
        drawBattleField();

        batch.begin();

        // Desenhar sprites
        drawSprites();

        batch.end();

        // Desenhar barras de HP
        drawHealthBars();

        // Desenhar UI
        stage.act(delta);
        stage.draw();
    }

    private void drawBattleField() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Fundo verde degradê estilo campo Pokémon
        shapeRenderer.setColor(0.6f, 0.9f, 0.6f, 1);
        shapeRenderer.rect(0, 300, Gdx.graphics.getWidth(), 400);

        shapeRenderer.setColor(0.5f, 0.8f, 0.5f, 1);
        shapeRenderer.rect(0, 300, Gdx.graphics.getWidth(), 200);

        shapeRenderer.end();
    }

    private void drawSprites() {
        // Sprite do oponente (canto superior direito)
        float opponentX = Gdx.graphics.getWidth() - 250;
        float opponentY = 500;

        if (opponentShakeTimer > 0) {
            opponentX += (Math.random() - 0.5) * 10;
            opponentY += (Math.random() - 0.5) * 10;
        }

        batch.draw(opponentSprite, opponentX, opponentY, 150, 150);

        // Sprite do jogador (canto inferior esquerdo)
        float playerX = 50;
        float playerY = 320;

        if (playerShakeTimer > 0) {
            playerX += (Math.random() - 0.5) * 10;
            playerY += (Math.random() - 0.5) * 10;
        }

        batch.draw(playerSprite, playerX, playerY, 150, 150);
    }

    private void drawHealthBars() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Barra de HP do oponente
        drawHealthBar(Gdx.graphics.getWidth() - 300, 680, opponentHP, 100, "OPONENTE");

        // Barra de HP do jogador
        drawHealthBar(50, 280, playerHP, 100, "VOCÊ");

        shapeRenderer.end();

        // Labels de HP
        batch.begin();
        smallFont.draw(batch, "HP: " + opponentHP + "/" + 100,
            Gdx.graphics.getWidth() - 200, 665);
        smallFont.draw(batch, "HP: " + playerHP + "/" + 100,
            150, 265);
        batch.end();
    }

    private void drawHealthBar(float x, float y, int currentHP, int maxHP, String name) {
        // Caixa de informação
        shapeRenderer.setColor(1, 1, 1, 0.9f);
        shapeRenderer.rect(x - 10, y - 10, 220, 60);

        // Borda da caixa
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        Gdx.gl.glLineWidth(3);
        shapeRenderer.rect(x - 10, y - 10, 220, 60);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Fundo da barra de HP
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, 200, 20);

        // Barra de HP colorida
        float hpPercentage = (float) currentHP / maxHP;
        if (hpPercentage > 0.5f) {
            shapeRenderer.setColor(Color.GREEN);
        } else if (hpPercentage > 0.2f) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.RED);
        }
        shapeRenderer.rect(x, y, 200 * hpPercentage, 20);

        // Borda da barra
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        Gdx.gl.glLineWidth(2);
        shapeRenderer.rect(x, y, 200, 20);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Nome
        batch.begin();
        smallFont.setColor(Color.BLACK);
        smallFont.draw(batch, name, x, y + 40);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (skin != null) skin.dispose();
        if (font != null) font.dispose();
        if (smallFont != null) smallFont.dispose();
        if (playerSprite != null) playerSprite.dispose();
        if (opponentSprite != null) opponentSprite.dispose();
    }
}
