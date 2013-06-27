<%@page import="java.util.Date"%>
<%@page import="de.TUBlr.persistence.Image"%>
<%@page import="de.TUBlr.persistence.EntityObject"%>
<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>cc-g03-tublr</title>
<script type="text/javascript" src="jQuery.js"></script>
<script type="text/javascript" src="magnitifier.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		$('.image-popup-vertical-fit').magnificPopup({
			type : 'image',
			closeOnContentClick : true,
			mainClass : 'mfp-img-mobile',
			image : {
				verticalFit : true
			}

		});

		$('.image-popup-fit-width').magnificPopup({
			type : 'image',
			closeOnContentClick : true,
			image : {
				verticalFit : false
			}
		});

		$('.image-popup-no-margins').magnificPopup({
			type : 'image',
			closeOnContentClick : true,
			closeBtnInside : false,
			mainClass : 'mfp-no-margins', // class to remove default margin from left and right side
			image : {
				verticalFit : true
			}
		});

	});

	function submit(blobKey) {
		var param = $("form[name=commentForm" + blobKey + "]").serialize();
		$.ajax({
			async : true,
			type : "POST",
			url : "TUBlr?do=addComment",
			data : param
		}).done(function(msg) {
			location.reload();
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
			<form class="post_form" action="${imageUploadUrl}" method="post"
				enctype="multipart/form-data">
				<h3>Post a new image:</h3>
				Image: <input name="uploadedFiles" type="file" size="35"
					maxlength="5000000" /> <br /> Message [optional]: <input
					name="message" type="text" size="30" maxlength="30" />
				<p>
					<input type="submit" class="submitButton" value="Post!" />
				</p>
			</form>
		</div>
		<div style="clear: both"></div>
		<hr />
		<c:forEach items="${resultMap}" var="entry" varStatus="index">
			<div class="comment_data yoxview">
				<div>
					<div class="comment_image">
						<br />
						<div style="width: 150px;">
							<a class="image-popup-vertical-fit"
								href="serve?blobKey=${entry.key['key']}"> <img
								src="serve?thumb=150&blobKey=${entry.key['key']}"
								alt="${entry.key['message']}" />
							</a>
						</div>
					</div>
					<div class="comment_form">
					<br />
						<div style="float:right; margin-left:120px; background-color:#d3d1d1;">
						<h5>Reply to this post:</h5>
							<form action="${imageUploadUrl}"
								name="commentForm${entry.key['key']}" method="post"
								enctype="multipart/form-data">
								Image: <input name="commentImage" type="file" size="25"
									maxlength="5000000" /><br /> Comment <input name="commentText"
									type="text" size="25" maxlength="140" /> <input type="hidden"
									name="blobKey" value="${entry.key['key']}" /> <input
									type="submit" value="comment" />
							</form>		
						</div>

						<div style="float:left;">
						<fmt:formatDate value="${entry.key['created']}"
							pattern="MM.dd.yyyy HH:mm:ss" />
						 <br> 
						 ${entry.key['message']}
						</div>
					</div>
				</div>
			</div>
			<div style="clear: both;">
				<h3 style="margin-left:160px; margin-top:5px;">Replies:</h3>
				<c:forEach items="${entry.value}" var="comment" varStatus="index">
					<div style="overflow: hidden; clear: both; margin-left: 170px; background-color:#d3d1d1">
						<div style="float: left;">
							<a class="image-popup-vertical-fit"
								href="serve?blobKey=${comment.imageKey}"><img
								src="serve?thumb=75&blobKey=${comment.imageKey}"
								alt="${comment.text}" /></a>
						</div>
						<div class="comment_text">
							<fmt:formatDate value="${comment['created']}"
								pattern="MM.dd.yyyy HH:mm:ss" />
							<br /> ${comment.text}
						</div>
					</div>
				</c:forEach>
			</div>
			<hr />
		</c:forEach>
	</div>
</body>
</html>
