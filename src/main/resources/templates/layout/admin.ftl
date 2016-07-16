<#import "/spring.ftl" as spring>
<#import "/lib/auth.ftl" as auth>
<#import "/lib/pagination.ftl" as pagination>

<#macro view title="admin" sidebar="users" header="">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>console - ${title}</title>
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/webjars/font-awesome/4.6.3/css/font-awesome.min.css">

	<link rel="stylesheet" type="text/css" href="/public/css/admin.css">
	<link rel="stylesheet" type="text/css" href="/public/css/simple-sidebar.css">

	<script src="/webjars/jquery/2.2.4/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="/webjars/coffee-script/1.10.0/coffee-script.min.js"></script>
	<script type="text/coffeescript" src="/public/script/common.coffee"></script>
	<script type="text/coffeescript">
		$.app = $.app or {}
		$.app.page = '${sidebar}'
	</script>
	<script type="text/coffeescript" src="/private/script/admin.coffee"></script>
    ${header}
</head>
<body>
<div class="navbar navbar-default navbar-custom">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">TODOLIST APPLICATION CONSOLE</a>
    <#--<a class="navbar-brand" href="#">Todolist Application Console</a>-->
		</div>
		<div class="navbar-right visible-sm visible-md visible-lg">
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle avater" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						<img src="/public/misc/avatar.png" class="img-responsive circle" alt="Responsive image">
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">修改密码</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="/logout">退出登录</a></li>
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
			<li><a href="/admin/console">控制台</a></li>
            <li><a href="/admin/users">用户管理</a></li>
			<li><a href="/admin/users/create">新建用户</a></li>
			<li><a href="/admin/tasks">任务管理</a></li>
		</ul>
	</div>
	<!-- /#sidebar-wrapper -->
	<!-- Page Content -->
	<div id="page-content-wrapper">
		<a href="#menu-toggle" id="menu-toggle" class="sidebar-menu"><i class="fa fa-angle-double-left"></i></a>
        <#nested>
	</div>
	<!-- /#page-content-wrapper -->
</div>

</body>
</html>
</#macro>
