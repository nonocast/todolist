/// <reference path="../app.d.ts"/>

export default class TaskImpl implements Task {
	id: number;
	title: string;
	status: string;
	category: string;

	constructor(p:{id: number, title: string, status: string, category: string}) {
		this.id = p.id;
		this.title = p.title;
		this.status = p.status;
		this.category = p.category;
	}

	public isCompleted(): boolean {
		return this.status == "CLOSE";
	}

	public toJson() {
		return {
			content: this.title,
			status: this.status
		};
	}
}
