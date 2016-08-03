<#import "/lib/auth.ftl" as auth>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${version}-${profile}</title>

	<link rel="shortcut icon" href="public/favicon.ico">
	<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="/public/css/todos.css">

	<script src="http://cdn.bootcss.com/react/15.2.1/react.min.js"></script>
	<script src="http://cdn.bootcss.com/react/15.2.1/react-dom.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/classnames/2.2.5/dedupe.min.js"></script>
</head>
<body>
<div class="full-screen">
	<div class="container no-padding">
		<div class="nav-menu">
			<ul class="nav nav-pills nav-stacked">
				<li class="text-center user-menu">
					<img src="/public/misc/user-10308319.png" alt="avatar" class="img-responsive img-circle img-avatar center-block">
					<a class="avatar-name" href="#" title="user">SheldonGe<span class="caret"></span></a>
				</li>
				<li class="active"><a href="#" title="today" class="menu-text">今日<span class="badge pull-right red-color">4</span></a></li>
				<li><a href="#" title="mid-term" class="menu-text">中期<span class="badge pull-right blue-color">4</span></a></li>
				<li><a href="#" title="long-term" class="menu-text">长期<span class="badge pull-right green-color">20</span></a></li>
				<li><a href="#" title="long-term" class="menu-text">已完成<span class="badge pull-right gray-color">4</span></a></li>
			</ul>
		</div>

		<div class="main-content">
			<section class="todoapp" token="${token}" from="${auth.username}"></section>
		</div>
	</div>
</div>
<script type="text/javascript" src="/public/script/bundle.js"></script>
</body>
</html>