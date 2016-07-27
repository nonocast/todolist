package cn.nonocast.api.form;

import cn.nonocast.base.FormBase;
import cn.nonocast.model.Task;

import javax.validation.constraints.Size;

public class TaskForm extends FormBase {
	private Long id = 0L;

	@Size(min=1, max=200, message="内容最多200字")
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
		this.content = task.getContent();
	}

	public Task push(Task task) {
		task.setContent(this.content);
		return task;
	}
}
