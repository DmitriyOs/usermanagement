<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Authorization</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<form method="post" action="${path}/authorization" role="form"
      style="width:300px; height:200px; margin: 120px auto; background: #8ec4d0;">
    <div class="form-group ${haserror}" style="width:200px; margin-left: auto; margin-right: auto;">
        <label class="control-label" for="login">Login</label>
        <input type="text" class="form-control" id="login" name="login" placeholder="Enter login">
    </div>
    <div class="form-group ${haserror}" style="width:200px; margin-left: auto; margin-right: auto;">
        <label class="control-label" for="password">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
    </div>
    <div style="width:200px; margin-left: auto; margin-right: auto;">
        <button type="submit" value="submit" class="btn btn-success">Sign in</button>
    </div>
</form>


<!-- Bootstrap core JavaScript
================================================== -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
