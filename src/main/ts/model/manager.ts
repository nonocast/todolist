/// <reference path="../app.d.ts"/>
import utils from "../misc/utils"

export default class TaskManagerImpl implements TaskManager {
	changed: () => void;
	tasks: Array<Task>;
	completedCount: number;
	lastTimer: number;

	constructor() {
		this.tasks = new Array<Task>();
		this.completedCount = -1;
	}

	public sync(tasks: Array<Task>, completedCount: number) {
		this.tasks = tasks;
		this.tasks.forEach(p => {p.statusChanged = this.onCompletedChanged.bind(this); });
		this.completedCount = completedCount;
	}

	private onCompletedChanged(task: Task) {
		if(task.status == "OPEN") {
			this.completedCount -= 1;
		} else {
			this.completedCount += 1;
		}

		clearTimeout(this.lastTimer);
		this.lastTimer = setTimeout((() => {
			console.log("setTimeout: "+Date.now());
			this.invalidate();
		}).bind(this), 3000);
	}

	// selected: [DAILY|SHORTTERM|LONGTERM|completed]
	public filter(category: string) : Array<Task> {
		if("completed" === category) {
			return this.tasks.filter(p => { return p.isCompleted() });
		} else {
			return this.tasks.filter(p => { return !p.isCompleted() && category === p.category });
		}
	}

	public count(category:string) : number {
		if("completed" === category) {
			return this.completedCount;
		} else {
			return this.tasks.filter(p => {
				return !p.isCompleted() && category === p.category
			}).length;
		}
	}

	public create(task: Task) {
		let snapshot = this.tasks;
		this.tasks.splice(0, 0, task);
		task.statusChanged = this.onCompletedChanged.bind(this);

		$.ajax({
			url: "/api/tasks",
			dataType: 'json',
			type: 'POST',
			data: {
				content: task.title,
				category: task.category
			},
			success: function(data: Task) {
				task.id = data.id;
				task.createdAt = data.createdAt;
				this.invalidate();
			}.bind(this),
			error: function(xhr, status, err) {
				console.error(this.props.url, status, err.toString());
				this.tasks = snapshot;
			}.bind(this)
		});

		this.invalidate();
	}

	public delete(task: Task) {
		let snapshot = this.tasks;
		this.tasks = this.tasks.filter((p) => { return task.id != p.id; });

		$.ajax({
			url: utils.join("/api/tasks", task.id),
			dataType: 'json',
			type: 'DELETE',
			success: function(data) {
				// do nothing
			}.bind(this),
			error: function(xhr, status, err) {
				console.error(this.props.url, status, err.toString());
				this.tasks = snapshot;
				this.invalidate();
			}.bind(this)
		});

		this.invalidate();
	}

	private invalidate() {
		if(this.changed!= null) {
			this.changed();
		}
	}
}
