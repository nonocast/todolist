package cn.nonocast.api.controller;

import cn.nonocast.api.form.TaskForm;
import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import cn.nonocast.repository.TaskRepository;
import cn.nonocast.misc.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("apiTaskController")
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping(method=RequestMethod.GET)
	@JsonView(Task.TaskView.class)
	public List<Task> index(@AuthenticationPrincipal User user) {
		return taskRepository.findByBelongsTo(user);
	}

	@RequestMapping(method=RequestMethod.POST)
	@JsonView(Task.TaskView.class)
	public ResponseEntity<?> create(@AuthenticationPrincipal User user, @Valid @ModelAttribute TaskForm form, Errors errors) {
		Task task;

		if(errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
		}

		try {
			task = new Task();
			task.setCategory(Task.TaskCategory.DAILY);
			task.setPriority(Task.TaskPriority.NORMAL);
			task.setStatus(Task.TaskStatus.OPEN);
			task.setBelongsTo(user);
			task.setBelongsToName(user.getName());
			taskRepository.save(form.push(task));
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}

		return ResponseEntity.ok(task);
	}

	@RequestMapping(value="/{id:[0-9]+}", method=RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") Long id) {
		try {
			taskRepository.delete(id);
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
		return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
	}

	@RequestMapping(value="/{id:[0-9]+}", method=RequestMethod.POST)
	@JsonView(Task.TaskView.class)
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @ModelAttribute TaskForm form, Errors errors) {
		Task task;

		if(errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
		}

		try {
			task = taskRepository.findOne(id);
			taskRepository.save(form.push(task));
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}

		return ResponseEntity.ok(task);

	}
}
