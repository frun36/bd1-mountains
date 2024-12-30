package com.frun36.mountains.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.frun36.mountains.api.model.RouteInfo;
import com.frun36.mountains.api.model.UserInfo;

@Service
public class UserInfoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserInfo getInfo(int userId) throws DataAccessException {
        String sql = """
                WITH leaderboard AS (SELECT id,
                                            rank() OVER (ORDER BY total_got_points DESC) as r,
                                            username,
                                            route_count,
                                            avg_route_len,
                                            total_got_points
                                     FROM mountains.app_user)
                SELECT *
                FROM leaderboard
                WHERE id = ?;
                                """;
        ;

        return jdbcTemplate.queryForObject(sql, (rs, rowId) -> new UserInfo(rs), userId);
    }

    public List<RouteInfo> getRoutes(int userId) throws DataAccessException {
        String sql = "SELECT r.id, r.name, u.username, r.time_modified, r.total_got_points " +
                "FROM mountains.route r JOIN mountains.app_user u ON r.user_id = u.id " +
                "WHERE u.id = ?";
        return jdbcTemplate.query(sql, (rs, rowId) -> new RouteInfo(rs), userId);
    }

    public List<UserInfo> getLeaderboard(String orderBy) throws DataAccessException, IllegalArgumentException {
        List<String> allowedOrderColumns = List.of("route_count", "avg_route_len", "total_got_points");

        if (!allowedOrderColumns.contains(orderBy))
            throw new IllegalArgumentException("Invalid order by column: " + orderBy);

        String sql = "SELECT id, rank() OVER (ORDER BY " + orderBy
                + " DESC) as r, username, route_count, avg_route_len, total_got_points FROM mountains.app_user ORDER BY r";
        return jdbcTemplate.query(sql, (rs, rowId) -> new UserInfo(rs));
    }
}
