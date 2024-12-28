package com.frun36.mountains.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record RouteTrailInfo(Integer ordinal, Integer id, String startPointName, String startPointType,
        Integer startPointAltitude,
        String endPointName, String endPointType, Integer endPointAltitude, String color, Integer gotPoints) {

    public RouteTrailInfo(ResultSet rs) throws SQLException {
        this(rs.getObject("ordinal", Integer.class), rs.getObject("id", Integer.class), rs.getString("sp_name"),
                rs.getString("sp_type"), rs.getObject("sp_altitude", Integer.class), rs.getString("ep_name"),
                rs.getString("ep_type"), rs.getObject("ep_altitude", Integer.class), rs.getString("color"),
                rs.getObject("got_points", Integer.class));
    }
}
