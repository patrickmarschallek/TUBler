<%@page import="de.TUBlr.persistence.Image"%>
<%@page import="de.TUBlr.persistence.EntityObject"%>
<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>cc-g03-tublr</title>
<script type="text/javascript" src="jQuery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>
<link href="default.css" rel="stylesheet" type="text/css" media="all"></link>
</head>

<body>
	<div id="header-wrap">
		<div id="header">
			<img src="images/tublr.png" alt="tublr"></img>
			<h3>A photo-sharing service by TU-Berlin!</h3>
		</div>
	</div>

	<div class="post_form_wrap">
		<form class="post_form"
			action="<%=blobstoreService.createUploadUrl("/upload")%>"
			method="post" enctype="multipart/form-data">
			<h3>Post a new image:</h3>
			Image: <input name="uploadedFiles" type="file" size="35"
				maxlength="50000000" /> <br /> Message [optional]: <input
				name="description" type="text" size="30" maxlength="30" />
			<p>
				<input type="submit" class="submitButton">Post!
				</button>
			</p>
		</form>
	</div>

	<c:forEach items="${imageList}" var="image" varStatus="index">
		<div id="content-wrapper">
			<div class="comment_data">
				<div class="comment_image">
					<a href="serve?blobKey=${image['key']}"><img
						src="serve?thumb=yes&blobKey=${image['key']}"
						alt="${image['message']}" /></a>
				</div>
				<div class="comment_text">FIXME</div>
			</div>

			<!-- 
			<div class="post">
			<div class="post_data">
				<div class="post_image">
					<a href="http://localhost:8888?do=addComment"><img src="http://localhost:8888?do=imageServe&imageKey=" /></a>
				</div>
				<div class="post_text">FIXME</div>
			</div>

			<div class="comment_data">
				<div class="comment_image">
					<a href="FIXME"><img src="FIXME" /></a>
				</div>
				<div class="comment_text">FIXME</div>
			</div>
			<div class="comment_form">
				<form action="FIXME" method="post" enctype="multipart/form-data">
					Image: <input name="comment_image" type="file" size="35"
						maxlength="5000000" /><br /> Message [optional]: <input
						name="comment_text" type="text" size="30" maxlength="30" /><br />
					<input type="submit" value="Post Comment!" /> <input type="hidden"
						name="type" value="comment" />
				</form>
			</div>
		</div>
		<hr />
		-->
		</div>
	</c:forEach>
</body>
</html>
