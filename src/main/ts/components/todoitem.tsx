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
					<input
						className="toggle"
						type="checkbox" />
					<label>
						{this.props.todo.title}
					</label>
					<button className="destroy" onClick={this.props.onDestroy} />
				</div>
			</li>
		);
	}
}
