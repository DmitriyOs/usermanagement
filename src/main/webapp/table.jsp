<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User list</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        function deleteUser(s) {
            window.location = "${path}/deleteuser?login="+s;
        }
    </script>
    <script>
        function editUser(s) {
            window.location = "${path}/formedit?login="+s;
        }
    </script>
</head>

<body>
<h3>Hello, ${login} with role ${role}</h3>

<table class="table table-bordered table-hover table-condensed" style="width: 800px;">
    <thead>
    <tr>
        <th>Login</th>
        <th>Role</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Mobile phone</th>
        ${th}
    </tr>
    </thead>
    <tbody>
    ${table}
    </tbody>
</table>
${button}


<!-- Bootstrap core JavaScript
================================================== -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>