package com.ufsmSistemas.tcgParadigma.interfaces;

public interface RegistroCallback {
    void onSuccess(String message);
    void onFailure(String errorMessage);
}
