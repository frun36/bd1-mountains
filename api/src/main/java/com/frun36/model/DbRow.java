package com.frun36.model;

import java.util.Map;

public interface DbRow {
    Integer getId();

    Map<String, Object> getMap();
}
