<#import "/lib/auth.ftl" as auth>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>home</title>
</head>
<body>
    <h1>home</h1>
    <p>${auth.username}</p>
    <h2>tasks</h2>

    <ul>
    <#list tasks as task>
        <li>${task.content}</li>
    </#list>
    </ul>
</body>
</html>