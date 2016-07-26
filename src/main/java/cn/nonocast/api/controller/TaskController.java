package cn.nonocast.api.controller;

import cn.nonocast.model.*;
import cn.nonocast.repository.TaskRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("apiTaskController")
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping("")
	@JsonView(Task.TaskView.class)
	public List<Task> index(@AuthenticationPrincipal User user) {
		return taskRepository.findByBelongsTo(user);
	}
}
