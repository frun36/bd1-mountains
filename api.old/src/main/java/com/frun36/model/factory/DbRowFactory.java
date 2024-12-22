package com.frun36.model.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.frun36.model.DbRow;

public abstract class DbRowFactory {
    public abstract DbRow fromResult(ResultSet rs) throws SQLException; 
}
