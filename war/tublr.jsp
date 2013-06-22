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
	function submit(blobKey) {
		var param = $("form[name=commentForm" + blobKey + "]").serialize();
		$.ajax({
			async : true,
			type : "POST",
			url : "TUBlr?do=addComment",
			data : param
		}).done(function(msg) {
			alert("Data Saved: " + param);
		});
	}
</script>
<link href="default.css" rel="stylesheet" type="text/css" media="all"></link>
</head>

<body>
	<div id="content-wrapper">
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
					<input type="submit" class="submitButton" value="Post!" />
				</p>
			</form>
		</div>
		<div style="clear: both"></div>
		<hr />
		<c:forEach items="${resultMap}" var="entry" varStatus="index">
			<div class="comment_data">
				<div class="comment_image">
					<a href="serve?blobKey=${entry.key['key']}"><img
						src="serve?thumb=yes&blobKey=${entry.key['key']}"
						alt="${entry.key['message']}" /></a>
				</div>
				<div class="comment_form">
					<form name="commentForm${entry.key['key']}" method="post"
						enctype="multipart/form-data">
						Comment <input name="commentText" type="text" size="30"
							maxlength="140" /> <input type="hidden" name="blobKey"
							value="${entry.key['key']}" />
					</form>
					<button onclick="submit('${entry.key['key']}');" type="button">Post
						Comment</button>
				</div>
			</div>
			<div style="clear:both;">
				<c:forEach items="${entry.value}" var="comment" varStatus="index">
					<div class="comment_text">
						<br />${comment.text}
					</div>
				</c:forEach>
			</div>
			<hr />
		</c:forEach>
	</div>
</body>
</html>
