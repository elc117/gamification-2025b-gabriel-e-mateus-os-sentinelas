package com.ufsmSistemas.tcgParadigma.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.ufsmSistemas.tcgParadigma.data.DataBaseAPI;
import com.ufsmSistemas.tcgParadigma.interfaces.ResponseCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Booster {
    private final List<Carta> cartasBooster = new ArrayList<>();
    private final int cartasEsperadas = 5;
    private int cartasCarregadas = 0;
    private BoosterCallback callback;

    public interface BoosterCallback {
        void onBoosterCompleto(List<Carta> cartas);
    }

    public List<Carta> getCartasBooster(){
        return cartasBooster;
    }

    public Booster(BoosterCallback callback) {
        this.callback = callback;
        Random random = new Random();
        DataBaseAPI api = new DataBaseAPI();

        System.out.println("[Booster] ===== INICIANDO CRIACAO DE BOOSTER =====");

        for (int i = 0; i < cartasEsperadas; i++) {
            final int index = i;
            Carta carta = new Carta();

            int numero = random.nextInt(106) + 1;
            if (numero <= 1) carta.setRaridade("Ultra Rara");
            else if (numero <= 6) carta.setRaridade("Rara");
            else carta.setRaridade("Comum");

            System.out.println("[Booster] Solicitando carta " + (i+1) + " - Raridade: " + carta.getRaridade());

            api.select(carta, new ResponseCallback() {
                @Override
                public void onResponse(JsonValue response) {
                    synchronized (cartasBooster) {
                        try {
                            carta.fromJson(response);
                            cartasBooster.add(carta);
                            cartasCarregadas++;

                            System.out.println("[Booster] Carta " + cartasCarregadas + "/" + cartasEsperadas + " carregada: " + carta.getNome());

                            if (cartasCarregadas >= cartasEsperadas) {
                                System.out.println("[Booster] TODAS AS CARTAS CARREGADAS!");
                                if (callback != null) {
                                    Gdx.app.postRunnable(() -> callback.onBoosterCompleto(new ArrayList<>(cartasBooster)));
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("[Booster] ERRO ao processar carta: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    System.err.println("[Booster] Erro ao carregar carta: " + errorMessage);
                }
            });
        }
    }
}
