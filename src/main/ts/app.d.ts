// Defines the interface of the structure of a task
interface Task {
	id: number,
	title: string;
	status: string;
	category: string;
	createdAt?: Date;
	closedAt?: Date;
	isCompleted(): boolean;
	toJson(): any;
}

// Defines the interface of the properties of the TodoItem component
interface TaskItemProps {
	url: string;
	//key : string,
	todo : Task;
	//editing? : boolean;
	//onSave: (val: any) => void;
	onDestroy: () => void;
	//onEdit: ()  => void;
	//onCancel: (event : any) => void;
	//onToggle: () => void;
}

// Defines the interface of the state of the TodoItem component
interface TaskItemState {
	editing?: boolean;
	completed?: boolean;
	editText?: string;
}

// Defines the TodoModel interface
interface ITodoModel {
	//key : any;
	todos : Array<Task>;
	//onChanges : Array<any>;
	//subscribe(onChange);
	//inform();
	//addTodo(title : string);
	//toggleAll(checked);
	//toggle(todoToToggle);
	//destroy(todo);
	//save(todoToSave, text);
	//clearCompleted();
}

/*
// Defines the interface of the properties of the Footer component
interface ITodoFooterProps {
	completedCount : number;
	onClearCompleted : any;
	nowShowing : string;
	count : number;
}

 */

// Defines the interface of the properties of the App component
interface AppProps {
	url: string;
	pollInterval: number;
}

// Defines the interface of the state of the App component
interface AppState {
	username?: string;
	email?: string;
	avatar?: string;
	dailyTasks?: Array<Task>;
	shortTasks?: Array<Task>;
	longTasks?: Array<Task>;
	completedTasks?: Array<Task>;
	selected?: string;
	selectedTitle?: string;
	completedCount?: number;
}
