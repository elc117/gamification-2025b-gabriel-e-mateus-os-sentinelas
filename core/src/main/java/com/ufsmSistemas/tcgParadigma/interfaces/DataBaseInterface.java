package com.ufsmSistemas.tcgParadigma.interfaces;

import java.sql.SQLException;

public interface DataBaseInterface {
    void insert(DataBaseEntity entity) throws SQLException;
}
