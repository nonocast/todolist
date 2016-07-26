/// <reference path="../../../../typings/globals/react-global/index.d.ts" />
/// <reference path="../app.d.ts"/>

import * as React from "react";

export class TodoItem extends React.Component<ITodoItemProps, ITodoItemState> {
	constructor(props : ITodoItemProps){
		super(props);
		this.state = { };
	}

	render() {
		return(
			<li>
				<div className="view">
					<label>
						{this.props.todo.title}
					</label>
				</div>
			</li>

			//<li className={React.addons.classSet({
      //   completed: this.props.todo.completed,
      //   editing: this.props.editing
      // })}>
			//	<div className="view">
			//		<input
			//			className="toggle"
			//			type="checkbox"
			//			checked={this.props.todo.completed}
			//			onChange={this.props.onToggle} />
			//		<label onDoubleClick={ e => this.handleEdit() }>
			//			{this.props.todo.title}
			//		</label>
			//		<button className="destroy" onClick={this.props.onDestroy} />
			//	</div>
			//	<input
			//		ref="editField"
			//		className="edit"
			//		value={this.state.editText}
			//		onBlur={ e => this.handleSubmit(e) }
			//		onChange={ e => this.handleChange(e) }
			//		onKeyDown={ e => this.handleKeyDown(e) } />
			//</li>
		);
	}
}
