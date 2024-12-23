package com.frun36.mountains.api.model;

import java.util.HashMap;
import java.util.Map;

public record AppUser(Integer id, String username, String password, Integer totalGotPoints) implements DbRow {
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", this.id);
        map.put("username", this.username);
        map.put("password", this.password);
        map.put("total_got_points", this.totalGotPoints);
        return map;
    }
}
