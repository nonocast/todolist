<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>TEMA1-项目一览</title>
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/webjars/font-awesome/4.6.3/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/console.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/simple-sidebar.css">
</head>
<body>
<div class="navbar navbar-default navbar-custom">
	<div class="container-fluid">
		<div class="navbar-header">
			<#--<a class="navbar-brand" href="#">TODOLIST APPLICATION CONSOLE</a>-->
            <a class="navbar-brand" href="#">Todolist Application Console</a>
		</div>
		<div class="navbar-right visible-sm visible-md visible-lg">
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle avater" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						<img src="/resources/misc/avatar.png" class="img-responsive circle" alt="Responsive image">
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">修改密码</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="#">退出登录</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<div id="wrapper">
	<!-- Sidebar -->
	<div id="sidebar-wrapper">
		<ul class="sidebar-nav">
			<li class="sidebar-brand">
				<p>用户中心</p>
			</li>
			<li>
				<a href="#">用户管理</a>
			</li>
			<li>
				<a href="#">新建用户</a>
			</li>
		</ul>
	</div>
	<!-- /#sidebar-wrapper -->
	<!-- Page Content -->
	<div id="page-content-wrapper">
		<a href="#menu-toggle" id="menu-toggle" class="sidebar-menu"><i class="fa fa-angle-double-left"></i></a>
		<section class="search">
			<div class="container-fluid">
				<div class="sub-nav">
					<a class="btn btn-default btn-green" href="new-project.html" title="create"><i class="fa fa-plus" aria-hidden="true""></i> 新建项目</a>
				</div>
			</div>
		</section>
		<section class="data-table">
			<div class="container-fluid">
				<table class="table table-striped">
					<thead>
					<tr>
						<th class="visible-sm visible-md visible-lg">项目编号</th>
						<th class="visible-sm visible-md visible-lg">级别</th>
						<th class="visible-lg">年份</th>
						<th class="visible-xs visible-sm visible-md visible-lg">项目名称</th>
						<th class="visible-lg">所在地</th>
						<th class="visible-lg">项目类型</th>
						<th class="visible-lg">项目金额</th>
						<th class="visible-lg">状态</th>
						<th class="visible-lg">项目经理</th>
						<th class="visible-lg">开发负责人</th>
						<th class="text-center td-btn">操作</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td class="visible-sm visible-md visible-lg">0001</td>
						<td class="visible-sm visible-md visible-lg">7</td>
						<td class="visible-lg">2015</td>
						<td class="visible-xs visible-sm visible-md visible-lg">武昌区人大会务管理系统</td>
						<td class="visible-lg">武汉</td>
						<td class="visible-lg">政府</td>
						<td class="visible-lg">4,000,000</td>
						<td class="visible-lg">进行中</td>
						<td class="visible-lg">张三</td>
						<td class="visible-lg">封昌俊</td>
						<td class="text-center td-btn"><a class="btn-table" href="project-info.html" title="编辑"><i class="fa fa-cog" aria-hidden="true"></a></td>
					</tr>
					</tbody>
				</table>
			</div>
		</section>
		<section class="paginations">
			<div class="container-fluid">
				<small class="pull-left pagination-text visible-sm visible-md visible-lg">现在是第1页，共1页，总共15条数据</small>
				<ul class="pagination pull-right">
					<li>
						<a href="#" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li>
						<a href="#" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</ul>
			</div>
		</section>
	</div>
	<!-- /#page-content-wrapper -->
</div>

<script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- Menu Toggle Script -->
<script>
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$('#wrapper').toggleClass('toggled');
		$('#menu-toggle').toggleClass('folded');
		if ($("#menu-toggle").hasClass('folded')) {
			$("#menu-toggle>i").removeClass('fa-angle-double-left');
			$("#menu-toggle>i").addClass('fa-angle-double-right');
		} else {
			$("#menu-toggle>i").addClass('fa-angle-double-left');
			$("#menu-toggle>i").removeClass('fa-angle-double-right');
		};
	});
</script>
</body>
</html>
