<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<jsp:include page="info/header.jsp"></jsp:include>

<!-- <div class="wrapper">
	<div class="content">
		<div class="container-fluid">
			<div class="row justify-content-center">
				<div class="col-md-8">
					<div class="card">
						<form action="">
							<h2 class="text-center">관리자 로그인</h2>
							
						</form>
					
					</div>
				</div>
			</div>
		</div>
	</div>


</div> -->
<body class="text-center login-body">
	<div class="login-main">
	<form action="${pageContext.request.contextPath}/admin/login_ok" class="form-signin mt-5">

		<h1 class="h3 mb-3 font-weight-normal">관리자 로그인</h1>
		<label for="inputEmail" class="sr-only">관리자 아이디</label>
		<input type="email" id="inputEmail" name="id" class="form-control" placeholder="관리자 아이디" required="" autofocus="">
		<label for="inputPassword" class="sr-only">Password</label>
		<input type="password" id="inputPassword" name="password" class="form-control" placeholder="비밀번호" required="">
		<div class="checkbox mb-3">
			<label> <input type="checkbox" value="remember-me"> Remember me </label>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit" style="color: #efbbcf; border-color: #efbbcf">로그인</button>
		<p class="mt-5 mb-3 text-muted">© 2017-2020</p>
	</form>
	</div>
</body>
<!--   Core JS Files   -->
    <script src="../resources/assets/js/jquery.3.2.1.min.js" type="text/javascript"></script>
	<script src="../resources/assets/js/bootstrap.min.js" type="text/javascript"></script>

	<!--  Charts Plugin -->
	<script src="../resources/assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="../resources/assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="../resources/assets/js/light-bootstrap-dashboard.js?v=1.4.0"></script>

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="../resources/assets/js/demo.js"></script>


</html>
