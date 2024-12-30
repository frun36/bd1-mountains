package com.frun36.mountains.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.frun36.mountains.api.model.RouteBrief;

@Service
public class RouteExplorerService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RouteBrief> userLongestRoutes() {
        String sql = """
WITH ranked AS (SELECT r.id                                                                         as route_id,
                       r.name,
                       u.id                                                                         as user_id,
                       u.username,
                       row_number() OVER (PARTITION BY u.username ORDER BY r.total_got_points DESC) as rn
                FROM mountains.route r
                         JOIN mountains.app_user u ON r.user_id = u.id)
SELECT route_id, name, user_id, username
FROM ranked
WHERE rn = 1;
                """;
        return jdbcTemplate.query(sql, (rs, idx) -> new RouteBrief(rs));
    }

    public List<RouteBrief> routesOver2k() {
        String sql = """
SELECT r.id                                    as route_id,
       r.name,
       u.id                                    as user_id,
       u.username
FROM mountains.route r
         JOIN mountains.app_user u on u.id = r.user_id
         JOIN mountains.route_trail rt ON rt.route_id = r.id
         JOIN mountains.trail t ON rt.trail_id = t.id
         JOIN mountains.point sp ON t.start_point_id = sp.id
         JOIN mountains.point ep ON t.end_point_id = ep.id
GROUP BY r.id, u.id
HAVING max(greatest(sp.altitude, ep.altitude)) >= 2000;
                """;
        return jdbcTemplate.query(sql, (rs, idx) -> new RouteBrief(rs));
    }

    public List<RouteBrief> routesContaining3Colors() {
        String sql = """
SELECT r.id                                    as route_id,
       r.name,
       u.id                                    as user_id,
       u.username
FROM mountains.route r
         JOIN mountains.app_user u on u.id = r.user_id
         JOIN mountains.route_trail rt ON rt.route_id = r.id
         JOIN mountains.trail t ON rt.trail_id = t.id
GROUP BY r.id, u.id
HAVING count(distinct t.color) >= 3;
                """;
        return jdbcTemplate.query(sql, (rs, idx) -> new RouteBrief(rs));
    }
}
