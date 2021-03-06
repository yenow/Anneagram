<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Enneagram</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/write.css">
</head>
<body>

	<c:if test="${login == null }">
		<script>
			alert('로그인이 필요합니다');
			location.href='${pageContext.request.contextPath}/member/login';
		</script>
	</c:if>
	

	<!-- nav -->
	<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #efbbcf; padding: 2px;">
		<a class="navbar-brand" href="#">Ennegram</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="#">
						Home<span class="sr-only">(current)</span>
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">Test</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="portfolio.html" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">에니어그램</a>
					<div class="dropdown-menu" aria-labelledby="dropdown04">
						<a class="dropdown-item" href="portfolio.html">에니어그램</a>
						<a class="dropdown-item" href="portfolio-single.html">역사</a>
						<a class="dropdown-item" href="portfolio-single.html">9가지 유형</a>
					</div>
				</li>

				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="${pageContext.request.contextPath}/board/boardList" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">게시판</a>
					<div class="dropdown-menu" aria-labelledby="dropdown04">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/board/boardList">공지사항</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/board/boardList">속닥속닥</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/board/boardList">자료실</a>
					</div>
				</li>

				<li class="nav-item">
					<a href="contact.html" class="nav-link">실시간 채팅</a>
				</li>
			</ul>
		</div>
	</nav>
	<!-- /nav -->

	<!-- content -->
	<div class="container-fluid">
		<div class="content-margin">
			<h2 class="text-center mb-3">게시판 등록</h2>
			<form action="${pageContext.request.contextPath}/board/boardUpdate_ok" method="post">
				<!-- mno -->
				<input type="hidden" name="mno" value="${login.mno}">
				<input type="hidden" name="id" value="${login.id}">
				<input type="hidden" name="nickname" value="${login.nickname}">
				
				<!-- category  -->
				<c:if test="${login.category == '관리자' }">
					
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<label class="input-group-text" for="inputGroupSelect01">카테고리</label>
						</div>
						<select class="custom-select" id="inputGroupSelect01" name="category">  <!-- select 태그안에 name속성있고, form태그 안이라면 전송됨 -->
							<option disabled="disabled">선택</option>
							<option value="1">공지사항</option>
							<option value="2">속닥속닥</option>
							<option value="3">Three</option>
						</select>
					</div>
				</c:if>
				<c:if test="${login.category != '관리자' }">
					<input type="hidden" name="category" value="속닥속닥">
				</c:if>
				<!-- /category  -->
				
				
				<div class="input-group mb-2 col-md-12">
					<!-- 제목 -->
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon1">제목</span>
					</div>
					<input type="text" class="form-control" value="${b.title }"  name="title" placeholder="제목" aria-label="Username" aria-describedby="basic-addon1">
					
					<!-- type정하기 -->
					<div class="input-group-prepend">
						<label class="input-group-text" for="inputGroupSelect01">카테고리</label>
					</div>
					<select class="custom-select" id="inputGroupSelect01" name="type">
						<option disabled="disabled">유형</option>
						<option value="1">1유형</option>
						<option value="2">2유형</option>
						<option value="3">3유형</option>
						<option value="4">4유형</option>
						<option value="5">5유형</option>
						<option value="6">6유형</option>
						<option value="7">7유형</option>
						<option value="8">8유형</option>
						<option value="9">9유형</option>
					</select>
				</div>
				<!-- /type정하기 -->

				<!-- 내용 -->
				<div class="col-md-12 mb-2"><textarea id="summernote" name="content"> ${b.content } </textarea></div>

				<div class="text-center">
					<input style="background-color: #efbbcf; padding: 2px;" type="submit" class="btn btn-primary " value="등록" >
				</div>
			</form>

		</div>
	</div>


	<script type="text/javascript">
		$('#summernote').summernote({
			height : 300, // 에디터 높이
			minHeight : 600, // 최소 높이
			maxHeight : null, // 최대 높이
			focus : true, // 에디터 로딩후 포커스를 맞출지 여부
			lang : "ko-KR", // 한글 설정
			placeholder : '최대 2048자까지 쓸 수 있습니다', //placeholder 설정
			callbacks : { //여기 부분이 이미지를 첨부하는 부분
				onImageUpload : function(files) {
					uploadSummernoteImageFile(files[0], this);
				}
			}
		});

		/**
		 * 이미지 파일 업로드
		 */
		function uploadSummernoteImageFile(file, editor) {
			data = new FormData();
			data.append("file", file);
			$.ajax({
				data : data,
				type : "POST",
				url : "/enneagram/uploadSummernoteImageFile",
				contentType : false,
				processData : false,
				success : function(data) {
					//항상 업로드된 파일의 url이 있어야 한다.
					console.log(data);
					$(editor).summernote('insertImage', data.url);
				}
			});
		}
		
	</script>



</body>
</html>

