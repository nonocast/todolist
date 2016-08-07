package cn.nonocast.api.controller;

import cn.nonocast.api.form.TaskForm;
import cn.nonocast.api.vm.TaskSummary;
import cn.nonocast.misc.EmptyJsonResponse;
import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import cn.nonocast.service.TaskService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import cn.nonocast.model.*;
import javax.validation.Valid;

@RestController("apiTaskController")
@RequestMapping("/api/tasks")
public class TaskController {
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@RequestMapping(method=RequestMethod.GET)
	public TaskSummary index(@AuthenticationPrincipal User user) {
		return taskService.findSummary(user);
	}

	@JsonView(ModelBase.API.class)
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> create(@AuthenticationPrincipal User user, @Valid @ModelAttribute TaskForm form, Errors errors) {
		Task task;

		if(errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
		}

		try {
			task = new Task();
			task.setPriority(Task.TaskPriority.NORMAL);
			task.setBelongsTo(user);
			task.setBelongsToName(user.getName());
			task.setBelongsToEmail(user.getEmail());
			taskService.save(form.push(task));
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}

		return ResponseEntity.ok(task);
	}

	@RequestMapping(value="/{id:[0-9]+}", method=RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") Long id) {
		try {
			Task task = taskService.findOne(id);
			taskService.delete(task);
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
		return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
	}

	@JsonView(ModelBase.API.class)
	@RequestMapping(value="/{id:[0-9]+}", method=RequestMethod.POST)
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @ModelAttribute TaskForm form, Errors errors) {
		Task task;

		if(errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
		}

		try {
			task = taskService.findOne(id);
			taskService.save(form.push(task));
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}

		return ResponseEntity.ok(task);

	}
}
