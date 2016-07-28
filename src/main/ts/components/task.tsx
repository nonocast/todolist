/// <reference path="../../../../typings/globals/react-global/index.d.ts" />
/// <reference path="../app.d.ts"/>

import * as constants from "../misc/constants"

export class TaskItem extends React.Component<TaskItemProps, TaskItemState> {
	constructor(props : TaskItemProps){
		super(props);
		this.state = {editing: false, editText: this.props.todo.title};
	}

	public edit() {
		this.setState({editing: true});
	}

	public onBlur() {
		this.setState({editing: false});
		this.update();
	}

	public onChange(event: __React.FormEvent) {
		var input: any = event.target;
		this.setState({editText: input.value});
	}

	public onKeyDown(event: __React.KeyboardEvent) {
		if (event.keyCode === constants.ESCAPE_KEY) {
			this.setState({editing: false, editText: this.props.todo.title});
		} else if (event.keyCode === constants.ENTER_KEY) {
			this.onBlur();
		}
	}

	public update() {
		let val = this.state.editText;
		this.props.todo.title = val;

		let snapshot = this.props.todo.title;

		if(val) {
			$.ajax({
				url: this.props.url,
				dataType: 'json',
				type: 'POST',
				data: {
					content: this.state.editText
				},
				success: function (task:Task) {
					this.props.todo.title = task.title;
					this.setState({editText: task.title});
				}.bind(this),
				error: function (xhr, status, err) {
					this.props.todo.title = snapshot;
					this.setState({editText: snapshot});
					console.error(this.props.url, status, err.toString());
				}.bind(this)
			});
		} else {
			this.props.onDestroy();
		}
	}

	public shouldComponentUpdate(nextProps : TaskItemProps, nextState : TaskItemState) {
		return (
			nextProps.todo !== this.props.todo ||
			nextState.editing !== this.state.editing ||
			nextState.editText !== this.state.editText
		);
	}

	public componentDidUpdate(prevProps : TaskItemProps, prevState: TaskItemState) {
		if (!prevState.editing && this.state.editing) {
			var node = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["editField"]);
			node.focus();
			node.setSelectionRange(node.value.length, node.value.length);
		}
	}

	public render() {
		return(
			<li className={classNames({
				completed: false,
				editing: this.state.editing
			})}>
				<div className="view">
					<input
						className="toggle"
						type="checkbox"
					/>
					<label onDoubleClick={this.edit.bind(this)}>
						{this.props.todo.title}
					</label>
					<button className="destroy" onClick={this.props.onDestroy} />
				</div>
				<input
					ref="editField"
					className="edit"
					value={this.state.editText}
					onBlur={this.onBlur.bind(this)}
				  onChange={this.onChange.bind(this)}
					onKeyDown={this.onKeyDown.bind(this) }
				/>
			</li>
		);
	}
}
