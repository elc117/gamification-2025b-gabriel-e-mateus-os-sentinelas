package com.ufsmSistemas.tcgParadigma.models;

import com.ufsmSistemas.tcgParadigma.data.Session;
import com.ufsmSistemas.tcgParadigma.utils.RecebeEnviaCartaApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Booster {
    private List<Carta> cartasBooster = new ArrayList<>();
    private final int cartasEsperadas = 5;
    private int cartasCarregadas = 0;
    private BoosterCallback callback;

    public interface BoosterCallback {
        void onBoosterCompleto(List<Carta> cartas);
    }

    public List<Carta> getCartasBooster() {
        return cartasBooster;
    }

    public Booster(BoosterCallback callback) {
        this.callback = callback;
        Random random = new Random();

        System.out.println("[Booster] ===== INICIANDO CRIACAO DE BOOSTER =====");

        for (int i = 0; i < cartasEsperadas; i++) {
            Carta carta = new Carta();
            int numero = random.nextInt(106) + 1;
            if (numero <= 1) carta.setRaridade("Ultra Rara");
            else if (numero <= 6) carta.setRaridade("Rara");
            else carta.setRaridade("Comum");

            System.out.println("[Booster] Solicitando carta " + (i + 1) + " - Raridade: " + carta.getRaridade());
            RecebeEnviaCartaApi receberCarta = new RecebeEnviaCartaApi();
            receberCarta.receberCartaApi(carta, novaCarta -> {
                synchronized (cartasBooster) {
                    cartasBooster.add(novaCarta);

                    if (cartasBooster.size() == cartasEsperadas && callback != null) {
                        callback.onBoosterCompleto(new ArrayList<>(cartasBooster));

                        // todas as cartas carregadas,enviar/verificar
                        Jogador jogador = Session.getInstance().getJogador();
                        RecebeEnviaCartaApi temp = new RecebeEnviaCartaApi();
                        System.out.println("[Booster] Jogador Id: " + jogador.getId());

                        for (Carta c : cartasBooster) {

                            temp.atualizaCartaApi(c, jogador);
                            System.out.println("[Booster] Carta atualizado com sucesso!");

                        }
                    }
                }
            });
        }
    }

}
