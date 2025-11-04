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

            api.select(carta);
        }
    }
}
