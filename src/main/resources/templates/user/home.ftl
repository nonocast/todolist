<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>${version}-${profile}</title>
	<script src="http://facebook.github.io/react/js/react.js"></script>
	<script src="http://facebook.github.io/react/js/react-dom.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
</head>
<body>
<div id="example"></div>
<script type="text/babel">
	var Hello = React.createClass({
		render: function() {
			return <div>Hello {this.props.name}</div>;
		}
	});

	ReactDOM.render(
        <Hello name="World" />,
		document.getElementById('example')
	);
</script>
</body>
</html>