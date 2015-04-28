<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit user</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        function cancel() {
            window.location = "${path}/start";
        }
    </script>

</head>

<body>
<h3>Enter new data for user ${oldlogin}</h3>

<form class="form-horizontal" method="post" action="${path}/edituser"
      role="form" style="width:500px;">
    <div><input type="hidden" id="oldlogin" name="oldlogin" value="${oldlogin}"></div>
    <div class="form-group">
        <label for="newlogin" class="col-sm-2 control-label" style="width:150px;">Login</label>

        <div class="col-sm-10" style="width:350px;">
            <input type="text" class="form-control" id="newlogin" name="newlogin" value="${newlogin}">
        </div>
    </div>
    <div class="form-group">
        <label for="newpassword" class="col-sm-2 control-label" style="width:150px;">Password</label>

        <div class="col-sm-10" style="width:350px;">
            <input type="text" class="form-control" id="newpassword" name="newpassword" value="${newpassword}">
        </div>
    </div>

    <div class="form-group">
        <label for="newrole" class="col-sm-2 control-label" style="width:150px;">Role</label>

        <div class="col-sm-10" style="width:350px;">
            <!--
            <input type="text" class="form-control" id="newrole" placeholder="${newrole}">
            -->
            <select id="newrole" name="newrole" class="form-control">
                ${options}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="newfullname" class="col-sm-2 control-label" style="width:150px;">Full Name</label>

        <div class="col-sm-10" style="width:350px;">
            <input type="text" class="form-control" id="newfullname" name="newfullname" value="${newfullname}">
        </div>
    </div>


    <div class="form-group">
        <label for="newemail" class="col-sm-2 control-label" style="width:150px;">Email</label>

        <div class="col-sm-10" style="width:350px;">
            <input type="text" class="form-control" id="newemail" name="newemail" value="${newemail}">
        </div>
    </div>

    <div class="form-group">
        <label for="newmobilephone" class="col-sm-2 control-label" style="width:150px;">Mobile Phone</label>

        <div class="col-sm-10" style="width:350px;">
            <input type="text" class="form-control" id="newmobilephone" name="newmobilephone" value="${newmobilephone}">
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10" style="margin-left: 5%">
            <button type="submit" class="btn btn-primary">Send</button>
            <button type="reset" class="btn btn-warning">Reset</button>
            <button type="button" class="btn btn-danger" onclick="cancel()">Cancel</button>
        </div>
    </div>
</form>


<!-- Bootstrap core JavaScript
================================================== -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>