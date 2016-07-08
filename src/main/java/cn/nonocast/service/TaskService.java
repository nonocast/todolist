package cn.nonocast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.nonocast.model.*;
import cn.nonocast.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;


    public List<Task> findByUser(User user) {
        List<Task> result = new ArrayList<>();

        return result;
    }
}
