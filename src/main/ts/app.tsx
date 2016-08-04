/// <reference path="../../../typings/globals/react-global/index.d.ts" />
/// <reference path="./app.d.ts"/>

import * as constants from "./misc/constants"
import utils from "./misc/utils"
import { TaskItem } from "./components/task";
import TaskImpl from "./model/task";

declare var Router : any;

class TodoApp extends React.Component<AppProps, AppState> {
	token: string;
	from: string;
	//username: string;
	//avatar: string;

	constructor(props) {
		super(props);

		console.log("v0.2.26");

		moment.locale('zh-cn');
		this.state = {
			dailyTasks: new Array<Task>(),
			shortTasks: new Array<Task>(),
			longTasks: new Array<Task>(),
			completedTasks: new Array<Task>(),
		};

		this.token = $('.todoapp').attr("token");
		this.from = $('.todoapp').attr("from");


		$.ajaxSetup({
			headers: { 'Token': this.token, 'From': this.from }
		});
	}

	public componentDidMount() {
		this.initRouter();

		this.sync();
		if(this.props.pollInterval > 0) {
			setInterval(this.sync.bind(this), this.props.pollInterval);
		}
	}

	private focusNewField() {
		var node = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]);
		node.focus();
		node.setSelectionRange(node.value.length, node.value.length);
	}

	private initRouter() {
		let setState = this.setState;
		let router = Router({
			'/': [setState.bind(this, { selected: "DAILY", selectedTitle: "今日事项" }), this.focusNewField.bind(this)],
			'/short': [setState.bind(this, { selected: "SHORTTERM", selectedTitle: "近期目标" }), this.focusNewField.bind(this)],
			'/long': [setState.bind(this, { selected: "LONGTERM", selectedTitle: "长期目标" }), this.focusNewField.bind(this)],
			'/completed': setState.bind(this, { selected: "completed", selectedTitle: "已完成" })
		});

		router.init('/');
	}

	public sync() {
		console.log("sync...  "+ Date.now())
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			type: "GET",
			cache: false,
			success: function (data) {
				// console.log(data);

				this.setState({username: data.user.name, email: data.user.email})
				this.setState({avatar: data.user.avatar == "" ? "/public/misc/user-10308319.png" : data.user.avatar});

				this.setState({completedCount: data.completedCount});
				this.setState({dailyTasks: data.active.filter(each => { return each.category === "DAILY" }).map(each => { return new TaskImpl(each); } )});
				this.setState({shortTasks: data.active.filter(each => { return each.category === "SHORTTERM" }).map(each => { return new TaskImpl(each); } )});
				this.setState({longTasks: data.active.filter(each => { return each.category === "LONGTERM" }).map(each => { return new TaskImpl(each); } )});
				this.setState({completedTasks: data.completed.map(each =>{ return new TaskImpl(each); } )});
			}.bind(this),
			error: function (xhr, status, err) {

			}.bind(this),
			statusCode: {
				401: () => {
					// window.location.href = "/";
				}
			}
		});
	}

	public handleNewTaskKeyDown(event) {
		if (event.keyCode !== constants.ENTER_KEY) return;
		event.preventDefault();

		var val = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value.trim();
		if(val) {
			this.create(new TaskImpl({ id: new Date().getTime(), title: val, status: "OPEN", category: this.state.selected}));
			ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value = "";
		}
	}

	public create(task: Task) {
		let snapshot = this.getCurrentTasks();

		$.ajax({
			url: this.props.url,
			dataType: 'json',
			type: 'POST',
			data: {
				content: task.title,
				category: task.category
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


	private getCurrentTasks() {
		let tasks = new Array<Task>();
		switch (this.state.selected) {
			case "DAILY":
				tasks = this.state.dailyTasks;
				break;
			case "SHORTTERM":
				tasks = this.state.shortTasks;
				break;
			case "LONGTERM":
				tasks = this.state.longTasks;
				break;
			case "completed":
				tasks = this.state.completedTasks;
				break;
		}
		return tasks;
	}

	public destroy(todo) {
		let snapshot = this.getCurrentTasks();
		let result = snapshot.filter((p) => { return todo.id != p.id; });

		$.ajax({
			url: utils.join(this.props.url, todo.id),
			dataType: 'json',
			type: 'DELETE',
			success: function(data) {
				this.sync();
			}.bind(this),
			error: function(xhr, status, err) {
				this.setState({tasks: snapshot});
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	}

	render() {
		let cx = classNames;
		let selected = this.state.selected;

		let sidebar = (
			<div className="nav-menu">
				<ul className="nav nav-pills nav-stacked">
					<li className="text-center user-menu">
						<img src={this.state.avatar} alt="avatar" className="img-responsive img-circle img-avatar center-block" />
						<a className="avatar-name" href="#" title="user">{this.state.username}<span className="caret"></span></a>
					</li>
					<li className={cx({active: selected === "DAILY"})}><a href="#/" title="today" className="menu-text">今日<span className="badge pull-right red-color">{this.state.dailyTasks.length}</span></a></li>
					<li className={cx({active: selected === "SHORTTERM"})}><a href="#/short" title="mid-term" className="menu-text">近期<span className="badge pull-right blue-color">{this.state.shortTasks.length}</span></a></li>
					<li className={cx({active: selected === "LONGTERM"})}><a href="#/long" title="long-term" className="menu-text">长期<span className="badge pull-right green-color">{this.state.longTasks.length}</span></a></li>
					<li className={cx({active: selected === "completed"})}><a href="#/completed" title="long-term" className="menu-text">已完成<span className="badge pull-right gray-color">{this.state.completedCount}</span></a></li>
				</ul>
			</div>
		);

		let footer = (
			<div className="footer">
				{this.state.selected === "completed" && this.state.completedTasks.length < this.state.completedCount ? <label>more</label> : null}
			</div>
		);

		let header = (
			<div>
				<header className="header">
					<h2>{this.state.selectedTitle}<small>{moment().format('YYYY年M月D日 dddd LT')}</small></h2>

					{this.state.selected === "completed" ? null :
						<input
							ref="newField"
							className="new-todo"
							placeholder="新增待办事项，按回车键保存..."
							onKeyDown={ e => this.handleNewTaskKeyDown(e) }
							autoFocus={true} />
					}
				</header>
			</div>
		);

		let taskItems = this.getCurrentTasks().map(function (each) {
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
			<div className="main-content">
				{header}
				<section className="main">
					{this.state.selected === "completed" ? null : <input type="checkbox" className="toggle-all" value="on" />}
					<ul className="todo-list">
						{taskItems}
					</ul>
				</section>
				{footer}
			</div>
		);

		return (
			<div className="full-screen">
				<div className="container no-padding">
					{sidebar}
					{main}
				</div>
			</div>
		);
	}
}

ReactDOM.render(
	<TodoApp url="/api/tasks" pollInterval={3000} />,
	$('.todoapp').get(0)
);
