package ru.practicum.ewm.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.EndpointHit;
import ru.practicum.ewm.ViewStats;
import ru.practicum.ewm.ViewsStatsRequest;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsRepositoryImpl implements StatsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ViewStatsMapper viewStatsMapper;

    @Override
    public void saveHit(EndpointHit hit) {
        jdbcTemplate.update("INSERT INTO stats (app, uri, ip, created) VALUES (?, ?, ?, ?)",
                hit.getApp(), hit.getUri(), hit.getIp(), Timestamp.valueOf(hit.getTimestamp()));
    }

    @Override
    public List<ViewStats> getStats(ViewsStatsRequest request) {
        String query = "SELECT app, uri, COUNT (ip) AS hits FROM stats WHERE (created >= ? AND created <= ?) ";
        return getViewStatsImpl(request, query);
    }

    @Override
    public List<ViewStats> getUniqueStats(ViewsStatsRequest request) {
        String query = "SELECT app, uri, COUNT (DISTINCT ip) AS hits FROM stats WHERE (created >= ? AND created <= ?) ";
        return getViewStatsImpl(request, query);
    }

    private List<ViewStats> getViewStatsImpl(ViewsStatsRequest request, String query) {
        if (!request.getUris().isEmpty()) {
            query += createUrisQuery(request.getUris());
        }
        query += " GROUP BY app, uri ORDER BY hits DESC";
        return jdbcTemplate.query(query, viewStatsMapper, request.getStart(), request.getEnd());
    }

    private String createUrisQuery(List<String> uris) {
        return "AND uri IN ('" + String.join("', '", uris) + "') ";
    }
}