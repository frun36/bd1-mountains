package com.frun36.mountains.api.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.frun36.mountains.api.model.RouteInfo;
import com.frun36.mountains.api.model.RouteTrailInfo;

@Service
public class RouteEditorService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RouteTrailInfo> get(int routeId) throws DataAccessException, SQLException {
        String sql = "SELECT rto.ordinal, t.id, sp.name as sp_name, sp.type as sp_type, sp.altitude as sp_altitude, "
                + "ep.name as ep_name, ep.type as ep_type, ep.altitude as ep_altitude, t.color, t.got_points "
                + "FROM mountains.route_trail_ordered rto "
                + "JOIN mountains.trail t ON t.id = rto.trail_id "
                + "JOIN mountains.point sp ON t.start_point_id = sp.id "
                + "JOIN mountains.point ep ON t.end_point_id = ep.id "
                + "WHERE route_id = ? "
                + "ORDER BY rto.ordinal";
        return jdbcTemplate.query(sql, (rs, rowId) -> new RouteTrailInfo(rs), routeId);
    }

    public RouteInfo getInfo(int routeId) throws DataAccessException, SQLException {
        String sql = "SELECT r.id, r.name, u.username, r.time_modified, r.total_got_points " +
                "FROM mountains.route r JOIN mountains.app_user u ON r.user_id = u.id " +
                "WHERE r.id = ?";
        
        return jdbcTemplate.queryForObject(sql, (rs, rowId) -> new RouteInfo(rs), routeId);
    }

    public List<RouteTrailInfo> getAppendable(int routeId) throws DataAccessException, SQLException {
        String sql = "WITH route_end_point_id AS ( " +
                "    SELECT max(t2.end_point_id) AS id " + // max returns null for no elements
                "    FROM mountains.route_trail rt " +
                "             JOIN mountains.trail t2 ON rt.trail_id = t2.id " +
                "    WHERE rt.route_id = ? " +
                "      AND rt.next_id IS NULL " +
                ") " +
                "SELECT cast(NULL as INT) as ordinal, " +
                "       t.id, " +
                "       sp.name           as sp_name, " +
                "       sp.type           as sp_type, " +
                "       sp.altitude       as sp_altitude, " +
                "       ep.name           as ep_name, " +
                "       ep.type           as ep_type, " +
                "       ep.altitude       as ep_altitude, " +
                "       t.color, " +
                "       t.got_points " +
                "FROM mountains.trail t " +
                "         JOIN mountains.point sp ON sp.id = t.start_point_id " +
                "         JOIN mountains.point ep ON ep.id = t.end_point_id " +
                "         JOIN route_end_point_id rsp ON rsp.id = t.start_point_id OR rsp.id IS NULL";
        return jdbcTemplate.query(sql, (rs, rowId) -> new RouteTrailInfo(rs), routeId);
    }

    public Integer append(int routeId, int trailId) throws DataAccessException {
        return jdbcTemplate.queryForObject("SELECT mountains.route_append(?, ?)", Integer.class, routeId, trailId);
    }

    public Integer popBack(int routeId) throws DataAccessException {
        return jdbcTemplate.queryForObject("SELECT mountains.route_pop_back(?)", Integer.class, routeId);
    }

    public List<RouteTrailInfo> getPrependable(int routeId) throws DataAccessException, SQLException {
        String sql = "WITH route_start_point_id AS ( " +
                "    SELECT max(t2.start_point_id) AS id " + // max returns null for no elements
                "    FROM mountains.route_trail rt " +
                "             JOIN mountains.trail t2 ON rt.trail_id = t2.id " +
                "    WHERE rt.route_id = ? " +
                "      AND rt.prev_id IS NULL " +
                ") " +
                "SELECT cast(NULL as INT) as ordinal, " +
                "       t.id, " +
                "       sp.name           as sp_name, " +
                "       sp.type           as sp_type, " +
                "       sp.altitude       as sp_altitude, " +
                "       ep.name           as ep_name, " +
                "       ep.type           as ep_type, " +
                "       ep.altitude       as ep_altitude, " +
                "       t.color, " +
                "       t.got_points " +
                "FROM mountains.trail t " +
                "         JOIN mountains.point sp ON sp.id = t.start_point_id " +
                "         JOIN mountains.point ep ON ep.id = t.end_point_id " +
                "         JOIN route_start_point_id rsp ON rsp.id = t.end_point_id OR rsp.id IS NULL";
        return jdbcTemplate.query(sql, (rs, rowId) -> new RouteTrailInfo(rs), routeId);
    }

    public Integer prepend(int routeId, int trailId) throws DataAccessException {
        return jdbcTemplate.queryForObject("SELECT mountains.route_prepend(?, ?)", Integer.class, routeId, trailId);
    }

    public Integer popFront(int routeId) throws DataAccessException {
        return jdbcTemplate.queryForObject("SELECT mountains.route_pop_front(?)", Integer.class, routeId);
    }
}
