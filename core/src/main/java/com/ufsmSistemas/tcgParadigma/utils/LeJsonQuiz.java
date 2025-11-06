package com.ufsmSistemas.tcgParadigma.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.models.quiz.Pergunta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeJsonQuiz {

    public static List<Pergunta> carregarPerguntas(String tema, String dificuldade) {
        List<Pergunta> perguntas = new ArrayList<Pergunta>(10);

        JsonReader reader = new JsonReader();
        JsonValue json = reader.parse(Gdx.files.internal("json/questoesJson/questaoJson.json"));

        JsonValue perguntasArray = json.get(tema).get(dificuldade);

        List<Integer> numeros = new ArrayList<Integer>();
        for (int i = 0; i < perguntasArray.size; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);
        List<Integer> selecionados = numeros.subList(0, Math.min(10, numeros.size()));

        for (int i : selecionados) {
            JsonValue obj = perguntasArray.get(i);
            String enunciado = obj.getString("pergunta");
            String respostaCerta = obj.getString("respostaCerta");

            List<String> respostasErradas = new ArrayList<String>();
            for (JsonValue errada : obj.get("respostaErrada")) {
                respostasErradas.add(errada.asString());
            }

            perguntas.add(new Pergunta(enunciado, respostaCerta, respostasErradas));
        }

        return perguntas;
    }
}
