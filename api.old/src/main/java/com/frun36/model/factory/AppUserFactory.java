package com.frun36.model.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.frun36.model.AppUser;

public class AppUserFactory extends DbRowFactory {
    @Override
    public AppUser fromResult(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        Integer totalGotPoints = rs.getInt("total_got_points");

        return new AppUser(id, username, password, totalGotPoints);
    }
}
