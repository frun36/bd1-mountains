package com.frun36.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbRowFactory {
    public abstract DbRow fromResult(ResultSet rs) throws SQLException; 
}
