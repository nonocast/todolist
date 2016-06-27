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
    <p>${auth.admin?then("i'm admin", "i'm not admin")}</p>
</body>
</html>