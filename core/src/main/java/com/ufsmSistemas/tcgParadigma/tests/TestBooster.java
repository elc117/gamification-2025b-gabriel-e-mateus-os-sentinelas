package com.ufsmSistemas.tcgParadigma.tests;

import com.ufsmSistemas.tcgParadigma.models.Booster;
import com.ufsmSistemas.tcgParadigma.models.Carta;

public class TestBooster {
    public static void main(String[] args) throws InterruptedException {
        Booster booster = new Booster(cartas -> {
            System.out.println("Booster completo!");
            for (Carta c : cartas) {
                System.out.println(c.getNome() + " (" + c.getRaridade() + ")");
            }
        });

    }
}
