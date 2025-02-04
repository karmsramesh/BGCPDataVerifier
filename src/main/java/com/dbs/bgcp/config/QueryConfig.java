package com.dbs.bgcp.config;


import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "queries")
public class QueryConfig {

    private Map<String, String> queries;

    /**
     * Method to fetch the query from queries map
     */
    public String getQuery(String queryName) {
        return queries.get(queryName);
    }
}
