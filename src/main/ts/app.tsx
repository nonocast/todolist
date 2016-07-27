/// <reference path="../../../typings/globals/react-global/index.d.ts" />
/// <reference path="./app.d.ts"/>

import * as $ from "jquery"
import * as React from "react";
import * as ReactDOM from "react-dom";
import * as constants from "./misc/constants"
import utils from "./misc/utils"
import { TodoItem } from "./components/todoItem";

class TodoApp extends React.Component<{}, {data: Array<ITodo>}> {
	token: string;

	constructor(props) {
		super(props);
		this.state = {data: new Array<ITodo>()};
		this.token = $('.todoapp').attr("token");
		$.ajaxSetup({
			headers: { 'TOKEN': this.token }
		});
	}

	componentDidMount() {
		let url = "/api/tasks";
		$.ajax({
			url: url,
			dataType: 'json',
			type: "GET",
			cache: false,
			success: function (data) {
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
			}.bind(this)
		});
	}

	public handleNewTodoKeyDown(event) {
		if (event.keyCode !== constants.ENTER_KEY) return;
		event.preventDefault();

		var val = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value.trim();
		if(val) {
			this.handleCommentSubmit({ id: new Date().getTime(), title: val});
			ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value = "";
		}

	}

	public handleCommentSubmit(todo: ITodo) {
		let snapshot = this.state.data;
		this.setState({data: snapshot.concat(todo)});

		$.ajax({
			url: "/api/tasks",
			dataType: 'json',
			type: 'POST',
			data: {
				content: todo.title
			},
			success: function(data) {

			}.bind(this),
			error: function(xhr, status, err) {
				this.setState({data: snapshot});
				console.error("POST /api/tasks", status, err.toString());
			}.bind(this)
		});
	}

	render() {
		console.log(this.state);
		let todoItems = this.state.data.map(function (each) {
			return (
				<TodoItem key={each.id} todo={each}/>
			);
		});

		let main = (
			<section className="main">
				<input className="toggle-all" type="checkbox"/>
				<ul className="todo-list">
					{todoItems}
				</ul>
			</section>
		);

		let footer = (
			<div className="footer">
				<span className="todo-count">4 items left</span>
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
						onKeyDown={ e => this.handleNewTodoKeyDown(e) }
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
	<TodoApp />,
	$('.todoapp').get(0)
);
