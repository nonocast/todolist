package cn.nonocast.api.vm;

import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class TaskSummary {
	@JsonView(User.WithoutPasswordView.class)
	private User user;

	@JsonView(Task.TaskView.class)
	private List<Task> active;

	@JsonView(Task.TaskView.class)
	private List<Task> completed;

	@JsonProperty
	private long completedCount;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Task> getActive() {
		return active;
	}

	public void setActive(List<Task> active) {
		this.active = active;
	}

	public List<Task> getCompleted() {
		return completed;
	}

	public void setCompleted(List<Task> completed) {
		this.completed = completed;
	}

	public long getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(long completedCount) {
		this.completedCount = completedCount;
	}
}
