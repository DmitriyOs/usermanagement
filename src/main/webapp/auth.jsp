<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
${error}
<form method="post" action="${path}/authorization">
  <p>Name <input type="text" name="login"></input></p>

  <p>Password <input type="text" name="password"></input></p>

  <p>&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value="submit"></input></p>
</form>
</body>
</html>
