/// <reference path="../../../typings/globals/react-global/index.d.ts" />
/// <reference path="./app.d.ts"/>

import * as constants from "./misc/constants"
import utils from "./misc/utils"
import { TaskItem } from "./components/task";
import TaskImpl from "./model/task";

class TodoApp extends React.Component<AppProps, AppState> {
	token: string;

	constructor(props) {
		super(props);

		console.log("v0.2.19");

		this.state = {tasks: new Array<Task>()};
		this.token = $('.todoapp').attr("token");
		$.ajaxSetup({
			headers: { 'Token': this.token }
		});
	}

	public componentDidMount() {
		this.sync();
		setInterval(this.sync.bind(this), 5000);
	}

	public sync() {
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			type: "GET",
			cache: false,
			success: function (data: Array<Task>) {
				let tasks = data.map(each => { return new TaskImpl(each); }, this);
				this.setState({tasks: tasks});
			}.bind(this),
			error: function (xhr, status, err) {

			}.bind(this),
			statusCode: {
				401: () => { window.location.href = "/"; }
			}
		});
	}

	public handleNewTaskKeyDown(event) {
		if (event.keyCode !== constants.ENTER_KEY) return;
		event.preventDefault();

		var val = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value.trim();
		if(val) {
			this.create(new TaskImpl({ id: new Date().getTime(), title: val, status: "OPEN"}));
			ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value = "";
		}
	}

	public create(task: Task) {
		let snapshot = this.state.tasks;
		this.setState({tasks: snapshot.concat(task)});

		$.ajax({
			url: this.props.url,
			dataType: 'json',
			type: 'POST',
			data: {
				content: task.title
			},
			success: function(data: Task) {
				this.sync();
			}.bind(this),
			error: function(xhr, status, err) {
				this.setState({tasks: snapshot});
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	}

	public destroy(todo) {
		let snapshot = this.state.tasks;
		let result = snapshot.filter((p) => { return todo.id != p.id; });
		this.setState({tasks: result});

		$.ajax({
			url: utils.join(this.props.url, todo.id),
			dataType: 'json',
			type: 'DELETE',
			success: function(data) { }.bind(this),
			error: function(xhr, status, err) {
				this.setState({tasks: snapshot});
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	}

	render() {
		let taskItems = this.state.tasks.map(function (each) {
			return (
				<TaskItem
					url={utils.join(this.props.url, each.id)}
					key={each.id}
					todo={each}
				  onDestroy={this.destroy.bind(this, each)}
				/>
			);
		}, this);

		let main = (
			<section className="main">
				<input className="toggle-all" type="checkbox" />
				<ul className="todo-list">
					{taskItems}
				</ul>
			</section>
		);

		let footer = (
			<div className="footer">
				<span className="todo-count">{this.state.tasks.length} items left</span>
			</div>
		);


		return (
			<div>
				<header className="header">
					<h1>todos</h1>
					<input
						ref="newField"
						className="new-todo"
						placeholder="What needs to be done?"
						onKeyDown={ e => this.handleNewTaskKeyDown(e) }
						autoFocus={true}
					/>
				</header>
				{main}
				{footer}
			</div>
		);
	}
}

ReactDOM.render(
	<TodoApp url="/api/tasks" pollInterval={5000} />,
	$('.todoapp').get(0)
);
