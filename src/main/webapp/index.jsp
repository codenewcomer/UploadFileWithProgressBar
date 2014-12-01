<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>Upload File with Progress Bar Demo</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script>
	function beginUpload() {
		$("#progress_bar").show();
		setInterval("getUploadMeter()", 500);
	}

	function getUploadMeter() {
		$.post("servlet/getUploadPercent", function(data) {
			var json = eval("(" + data + ")");
			jQuery("#progress").css("width", json.percentage / 100 * 200 + "px");
			jQuery("#msg").css("padding", "1px").css("width", "400px").css("height", "20px").html("Finishing: " + json.percentage + "%");
		});
	}
</script>
</head>
<body>
	<h2>Upload File with Progress Bar</h2>
	<form action="servlet/uploadServlet" method="post" enctype="multipart/form-data" onsubmit="beginUpload()" target="_self">
		<input type="file" name="formFile" style="width:300px; height:30px; font-size: 14px">
		<br />
		<br />
		Progress Bar goes here...
		<br />

		<div id="progress_bar"
			style="width: 400px; height: 20px; display: none; border: 1px solid black;">
			<div id="progress"
				style="background-color: red; height: 20px; width: 0px;"></div>
		</div>

		<br />
		<input type="submit" value="Upload" style="width:100px; height:30px; font-size: 20px">
		<div id="msg"></div>
	</form>
</body>
</html>