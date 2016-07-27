package cn.nonocast.api.form;

import cn.nonocast.base.*;
import cn.nonocast.model.*;

public class TaskForm extends FormBase {
	private Long id = 0L;
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public void pull(Task task) {
		this.id = task.getId();
		this.content = task.getContent();
	}

	public Task push(Task task) {
		task.setContent(this.content);
		return task;
	}
}
