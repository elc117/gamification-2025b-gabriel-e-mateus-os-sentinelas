package com.ufsmSistemas.tcgParadigma.models;

import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Booster {
    private final List<Carta> cartasBooster = new ArrayList<>();
    private final int cartasEsperadas = 5;
    private int cartasCarregadas = 0;

    public interface BoosterCallback {
        void onBoosterCompleto(List<Carta> cartas);
    }

    public Booster(BoosterCallback callback) {
        Random random = new Random();
        DataBaseAPI api = new DataBaseAPI();

        System.out.println("[Booster] ===== INICIANDO CRIACAO DE BOOSTER =====");

        for (int i = 0; i < cartasEsperadas; i++) {
            final int index = i;
            Carta carta = new Carta();

            int numero = random.nextInt(107) + 1;
            if (numero <= 2) carta.setRaridade("Ultra Rara");
            else if (numero <= 7) carta.setRaridade("Rara");
            else carta.setRaridade("Comum");

            System.out.println("[Booster] Solicitando carta " + (i+1) + " - Raridade: " + carta.getRaridade());

            api.select(carta, response -> {
                System.out.println("[Booster] *** CALLBACK API RECEBIDO para carta " + (index+1) + " ***");

                synchronized (cartasBooster) {
                    try {
                        carta.fromJson(response);
                        cartasBooster.add(carta);
                        cartasCarregadas++;

                        System.out.println("[Booster] Carta adicionada: " + cartasCarregadas + "/" + cartasEsperadas);
                        System.out.println("[Booster]   Nome: " + carta.getNome());
                        System.out.println("[Booster]   Raridade: " + carta.getRaridade());
                        System.out.println("[Booster]   Caminho: " + carta.getCaminhoImagem());

                        if (cartasCarregadas >= cartasEsperadas) {
                            System.out.println("[Booster] ========================================");
                            System.out.println("[Booster] TODAS AS " + cartasEsperadas + " CARTAS CARREGADAS!");
                            System.out.println("[Booster] Tamanho da lista: " + cartasBooster.size());
                            System.out.println("[Booster] CHAMANDO CALLBACK...");
                            System.out.println("[Booster] ========================================");

                            callback.onBoosterCompleto(new ArrayList<>(cartasBooster));

                            System.out.println("[Booster] Callback executado com sucesso!");
                        }
                    } catch (Exception e) {
                        System.err.println("[Booster] ERRO ao processar carta: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
