package com.ufsmSistemas.tcgParadigma.interfaces;

public interface DataBaseInterfaceAPI {
    void insert(DataBaseEntityAPI entity);
    void update(DataBaseEntityAPI entity);
    void select(DataBaseEntityAPI entity);
}
