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
    <h2>all users</h2>

    <ul>
    <#list users as user>
        <li>${user.name} - ${user.email}</li>
    </#list>
    </ul>
</body>
</html>