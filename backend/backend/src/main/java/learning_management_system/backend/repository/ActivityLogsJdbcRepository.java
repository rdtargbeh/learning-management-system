package learning_management_system.backend.repository;

import learning_management_system.backend.entity.ActivityLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActivityLogsJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ActivityLogs> findAll() {
        return jdbcTemplate.query("SELECT * FROM activity_logs", new BeanPropertyRowMapper<>(ActivityLogs.class));
    }
}

