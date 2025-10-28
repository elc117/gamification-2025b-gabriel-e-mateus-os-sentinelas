package com.ufsmSistemas.tcgParadigma.Classes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.*;
import java.io.FileReader;
import java.util.*;
import java.io.FileNotFoundException;

public class LeJsonQuiz {
    public static List<Pergunta> carregarPerguntas(String tema, String dificuldade) throws FileNotFoundException {
        List<Pergunta> perguntas = new ArrayList<>(10);
        Gson gson = new Gson();
        FileReader reader = new FileReader("assets/json/questoesJson/questaoJson.json");
        JsonObject json = gson.fromJson(reader, JsonObject.class);

        JsonArray perguntasArray = json
            .getAsJsonObject(tema)
            .getAsJsonArray(dificuldade);

        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);
        List<Integer> selecionados = numeros.subList(0, 10);

        for (int i = 0; i < 10; i++) {

            JsonObject obj = perguntasArray.get(selecionados.get(i)).getAsJsonObject();

            String enunciado = obj.get("pergunta").getAsString();
            String respostaCerta = obj.get("respostaCerta").getAsString();
            JsonArray erradas = obj.getAsJsonArray("respostaErrada");

            List<String> respostasErradas = new ArrayList<>();
            for (JsonElement e : erradas) {
                respostasErradas.add(e.getAsString());
            }

            Pergunta p = new Pergunta(enunciado, respostaCerta, respostasErradas);
            perguntas.add(p);
        }
        return perguntas;
    }
}
