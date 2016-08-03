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
	username: string;
	avatar: string;

	constructor(props) {
		super(props);

		console.log("v0.2.25");

		moment.locale('zh-cn');
		this.state = {tasks: new Array<Task>()};
		this.token = $('.todoapp').attr("token");
		this.from = $('.todoapp').attr("from");
		this.username = $('.todoapp').attr("username");

		this.avatar = $('.todoapp').attr("avatar");
		if(this.avatar == "") {
			this.avatar = "/public/misc/user-10308319.png";
		}

		$.ajaxSetup({
			headers: { 'Token': this.token, 'From': this.from }
		});
	}

	public componentDidMount() {
		this.initRouter();

		this.sync();
		setInterval(this.sync.bind(this), this.props.pollInterval);
	}

	private initRouter() {
		let setState = this.setState;
		let router = Router({
			'/': [setState.bind(this, { filter: "DAILY", filterName: "今日事项" }), this.sync.bind(this)],
			'/short': [setState.bind(this, { filter: "SHORTTERM", filterName: "近期目标" }), this.sync.bind(this)],
			'/long': [setState.bind(this, { filter: "LONGTERM", filterName: "长期目标" }), this.sync.bind(this)],
			'/completed': [setState.bind(this, { filter: "completed", filterName: "已完成" }), this.sync.bind(this)]
		});

		router.init('/');
	}

	public sync() {
		console.log("sync...  "+Date.now())
		$.ajax({
			url: this.props.url,
			data: {status: this.state.filter === "completed" ? "CLOSE" : "OPEN" },
			dataType: 'json',
			type: "GET",
			cache: false,
			success: function (data: Array<Task>) {
				let tasks = data
					.filter(each => { return this.state.filter === "completed" || this.state.filter === each.category })
					.map(each => { return new TaskImpl(each); }, this);
				this.setState({tasks: tasks});
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
			this.create(new TaskImpl({ id: new Date().getTime(), title: val, status: "OPEN", category: "DIALY"}));
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
				content: task.title,
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
		let cx = classNames;
		let filter = this.state.filter;

		let sidebar = (
			<div className="nav-menu">
				<ul className="nav nav-pills nav-stacked">
					<li className="text-center user-menu">
						<img src={this.avatar} alt="avatar" className="img-responsive img-circle img-avatar center-block" />
						<a className="avatar-name" href="#" title="user">{this.username}<span className="caret"></span></a>
					</li>
					<li className={cx({active: filter === "DAILY"})}><a href="#/" title="today" className="menu-text">今日<span className="badge pull-right red-color">4</span></a></li>
					<li className={cx({active: filter === "SHORTTERM"})}><a href="#/short" title="mid-term" className="menu-text">近期<span className="badge pull-right blue-color">4</span></a></li>
					<li className={cx({active: filter === "LONGTERM"})}><a href="#/long" title="long-term" className="menu-text">长期<span className="badge pull-right green-color">20</span></a></li>
					<li className={cx({active: filter === "completed"})}><a href="#/completed" title="long-term" className="menu-text">已完成<span className="badge pull-right gray-color">4</span></a></li>
				</ul>
			</div>
		);

		let footer = (
			<div className="footer">
				<span className="todo-count">{this.state.tasks.length} items left</span>
			</div>
		);

		let header = (
			<div>
				<header className="header">
					<h2>{this.state.filterName}<small>{moment().format('YYYY年M月D日 dddd LT')}</small></h2>

					{this.state.filter === "completed" ? null :
						<input
							ref="newField"
							className="new-todo"
							placeholder="新增待办事项，按回车键保存..."
							onKeyDown={ e => this.handleNewTaskKeyDown(e) }
							autoFocus={true}/>
					}
				</header>
			</div>
		);

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
			<div className="main-content">
				{header}
				<section className="main">
					{this.state.filter === "completed" ? null : <input type="checkbox" className="toggle-all" value="on" />}
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
	<TodoApp url="/api/tasks" pollInterval={7000} />,
	$('.todoapp').get(0)
);
