package cn.nonocast.repository;

import cn.nonocast.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBelongsTo(User user);
}
