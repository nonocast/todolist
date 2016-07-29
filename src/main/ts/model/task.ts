/// <reference path="../app.d.ts"/>

export default class TaskImpl implements Task {
	id:number;
	title:string;
	status:string;

	constructor(p:{id: number, title: string, status: string}) {
		this.id = p.id;
		this.title = p.title;
		this.status = p.status;
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
