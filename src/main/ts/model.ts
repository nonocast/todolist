/// <reference path="./app.d.ts"/>

namespace app.models {

	export class TodoModel implements ITodoModel {
		public todos : Array<ITodo>;    // a list of tasks

		constructor() {
			this.todos = [
				{id:1, title:"todo item 1"},
				{id:2, title:"todo item 2"}
			];
		}
	}
}
