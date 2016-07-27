package cn.nonocast.api.controller;

import cn.nonocast.api.form.TaskForm;
import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import cn.nonocast.repository.TaskRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("apiTaskController")
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping(value="", method= RequestMethod.GET)
	@JsonView(Task.TaskView.class)
	public List<Task> index(@AuthenticationPrincipal User user) {
		return taskRepository.findByBelongsTo(user);
	}

	@RequestMapping(value="", method=RequestMethod.POST)
	@JsonView(Task.TaskView.class)
	public Task create(@AuthenticationPrincipal User user, @ModelAttribute TaskForm form) {
		Task task = null;
		try {
			task = new Task();
			task.setCategory(Task.TaskCategory.DAILY);
			task.setPriority(Task.TaskPriority.NORMAL);
			task.setStatus(Task.TaskStatus.OPEN);
			task.setBelongsTo(user);
			task.setBelongsToName(user.getName());
			taskRepository.save(form.push(task));
		} catch (DataAccessException ex) {
			return null;
		}

		return task;
	}
}
