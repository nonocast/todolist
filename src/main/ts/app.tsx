import * as $ from "jquery"
import * as React from "react";
import * as ReactDOM from "react-dom";
import { TodoItem } from "./components/todoItem";

class TodoApp extends React.Component<{}, {data: Array<ITodo>}> {
	constructor(props) {
		super(props);
		this.state = { data: new Array<ITodo>() };
	}

	componentDidMount() {
		let url = "/api/tasks";
		$.ajax({
			url: url,
			dataType: 'json',
			cache: false,
			success: function(data) {
				this.setState({data: data});
			}.bind(this),
			error: function(xhr, status, err) {
			}.bind(this)
		});
	}

	render() {
		console.log(this.state);
		let todoItems = this.state.data.map(function(each) {
			return (
				<TodoItem key={each.id} todo={each} />
			);
		});

		return(
			<div>
				{todoItems}
			</div>
		);
	}
}

ReactDOM.render(
	<TodoApp />,
	$('.todoapp').get(0)
);

