<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
Member member=(Member)request.getAttribute("member"); 
%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<style type="text/css">
.pwd {
	margin-top: 20px;
}

.login {
	margin-top: 15px;
}

#memIdShow {
   color: red;
}
</style>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
			<![endif]-->

</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">

				<div align="center">
					<Img src="<%=request.getContextPath()%>/front_end/images/logo.png" height="250px" width="400px" />
				</div>

				<form class="" action="<%=request.getContextPath()%>/Login" method="post">


					<div class="form-group">
						<label for="memId" class="cols-sm-2 control-label">�b��</label><span
							id="memIdShow"> <c:if test="${not empty errorMsgs}">
									&nbsp;&nbsp;�b���K�X���~
							</c:if>

						</span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa"
									aria-hidden="true"></i></span> <input type="text" class="form-control"
									name="memId" id="memId" value="petym2" placeholder="�п�J�b��" required />
							</div>
						</div>
					</div>


					<div class="form-group pwd">
						<label for="memId" class="cols-sm-2 control-label">�K�X</label><span
							id="memIdShow"></span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa"
									aria-hidden="true"></i></span> <input type="password"
									class="form-control" name="memPwd" id="memId" value="123456"
									placeholder="�п�J�b��" required />
							</div>
						</div>
					</div>

					<div class="checkbox">
						<label> <input type="checkbox"> �O����
						</label>
					</div>

					<input class="btn btn-primary btn-lg btn-block login-button login"
						type="submit" value="�n��">
					<div>
						<a href="#" class="btn btn-link">�ѰO�K�X</a> <a href="register.jsp"
							class="btn btn-link">���U</a>
					</div>
				</form>

			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>