package com.ufsmSistemas.tcgParadigma.interfaces;

import com.ufsmSistemas.tcgParadigma.models.Carta;
import java.util.List;

public interface BoosterCallback {
    void onBoosterCompleto(List<Carta> cartas);
}
