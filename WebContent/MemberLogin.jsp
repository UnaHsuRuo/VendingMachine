<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>MemberLogin</title>
<style type="text/css">
* {
    padding: 0;
    margin: 0;
    font-family: 'Open Sans Light';
}
html {
    height: 100%;
}

body {
    height: 100%;
}

.container {
    height: 100%;
    background-image: linear-gradient(to right,#fbc2eb,#a6c1ee);
}

.login-wrapper {
    background-color: #fff;
    width: 250px;
    height: 500px;
    padding: 0 50px;
    position: relative;
    left: 50%;
    border-radius: 15px;
    top:50%;
    transform: translate(-50%,-50%);
}
.login-wrapper .header {
    font-size:  30px;
    font-weight: bold;
    text-align: center;
    line-height: 200px;
}

.login-wrapper .form-wrapper .input-item {
    display: block;
    width: 100%;
    margin-bottom: 20px;
    border: none;
    padding: 10px;
    border-bottom: 2px solid rgb(128,125,125);
    font-size: 15px;
    outline: none;
}

.login-wrapper .form-wrapper .input-item::placeholder {
    text-transform: uppercase;
}

.login-wrapper .form-wrapper .btn {
    text-align: center;
    width: 100%;
    padding: 10px;
    margin-top: 40px;
    background-image: linear-gradient(to right,#fbc2eb,#a6c1ee);;
    color: #fff;
    font-size: 15px;
    outline: none;
    border: none;
}
    </style>
    
	<script type="text/javascript">
	$(document).ready(function(){
	    if (${not empty requestScope.loginMsg}) {
	        alert("${requestScope.loginMsg}");
	    }
	});
	</script>
	
</head>
<body>
 <div class="container">
        <div class="login-wrapper">
            <div class="header">Login</div>
            <div class="form-wrapper">
				<form class="form" action="LoginAction.do" method="post">
					<input type="hidden" name="action" value="login" />
					<input type="hidden" name="pageNo" value="1">
					<input type="text" name="username" placeholder="username" class="input-item" /> <br/><br/>
					<input type="password" name="password" placeholder="password" class="input-item" /> <br/><br/>
					<div> <input class="btn" type="submit" value="login" /> </div>
					
				</form>
				
			</div>
			</div>
			
		</div>

</body>
</html>