package com.ufsmSistemas.tcgParadigma.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DataBaseEntity {
    String getInsertSQL();
    void setInsertParameters(PreparedStatement pstmt) throws SQLException;

    String getUpdateSQL();
    void setUpdateParameters(PreparedStatement pstmt) throws SQLException ;

    String getSelectSQL();
    void setSelectParameters(PreparedStatement pstmt) throws SQLException;
}
