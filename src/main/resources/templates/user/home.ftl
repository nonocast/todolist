<#import "/lib/auth.ftl" as auth>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>${version}-${profile}</title>
	<script src="/webjars/react/15.2.1/react.js"></script>
	<script src="/webjars/react/15.2.1/react-dom.js"></script>
	<script src="/webjars/babel-core/5.8.19/browser.js"></script>
	<script src="/webjars/jquery/2.2.4/jquery.js"></script>
</head>
<body>
<h1>${auth.username}</h1>
<div id="content"></div>
<script type="text/babel">
	// tutorial1.js
	var CommentBox = React.createClass({
		render: function() {
			return (
				<div className="commentBox">
					Hello, world! I am a CommentBox.
				</div>
			);
		}
	});
	ReactDOM.render(
		<CommentBox />,
		document.getElementById('content')
	);
</script>
</body>
</html>