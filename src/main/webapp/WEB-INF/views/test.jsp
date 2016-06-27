<!DOCTYPE html>
<%@page session="false" %>
<html lang="en">
<head>
<title>Welcome Online Exam App</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<style type="text/css">
.dataCellDiv {
	max-height: 300px;
	overflow-y: scroll;
	overflow-x: hidden;
	width: auto;
}

.tab-content {
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom: 1px solid #ddd;
	height: 100%;
	padding: 10px;
}

table.tab2 {
	border-collapse: collapse;
}

table.tab2 th, table.tab td {
	padding: 10px;
}

table.tab2 tr:first-child th {
	background-color: #0080FF;
	height: 50px;
	color: #ffffff;
}

table.dataCell {
	padding: 10px;
	background-color: #EFF5FB;
}

table.dataCell {
	border-radius: 10px;
	padding: 0px;
	margin-left: -10px;
	margin-top: 5px;
	margin-bottom: 5px;
}

table {
	width: 100%;
}

table.tab2 td, table.dataCell td {
	width: 500px;
	padding-left: 12px;
}

.chkbox {
	text-align: right;
	margin-right: 30px;
}

.hidden {
	dispay: none;
}

.questionBox {
	border: solid 1px black;
	width: 80%;
	padding: 20px;
	height: 300px;
	overflow-y: scroll;
	background-color: #cccccc;
	border-radius: 10px;
	margin-bottom: 10px;
	height: 520px;
}

.current {
	float: left;
	border: solid 1px black;
	width: 50px;
	height: 50px;
	margin: 2px 2px 2px 2px;
	background-color: #666666;
	color: white;
	border-radius: 10px;
	background-color: #666666;
}

.notcurrent {
	float: left;
	border: solid 1px black;
	width: 50px;
	height: 50px;
	margin: 2px 2px 2px 2px;
	background-color: yellow;
}

.attempted {
	float: left;
	border: solid 1px black;
	width: 50px;
	height: 50px;
	margin: 2px 2px 2px 2px;
	background-color: green;
}

.questionDesc {
	border-radius: 20px;
	background-color: #ffffff;
	color: #666666;
	overflow-y: scroll;
	padding: 20px 20px 20px 20px;
	white-space: pre;
	max-height: 350px;
}

.questionOption {
	margin-top: 10px;
	border-radius: 20px;
	background-color: #666666;
	color: #ffffff;
	overflow-y: scroll;
	padding: 20px 20px 20px 20px;
	border-radius: 20px;
	max-height: 250px;
}

textarea {
	border-radius: 20px;
	padding: 10px;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		if ($('#successMsgBox').text() != "") {
			$('#successMsgBox').delay(1000).fadeOut(3000);
		}

		if ($('#errMsgBox').text() != "") {
			$('#errMsgBox').delay(1000).fadeOut(3000);
		}

		if ($('.tab').size() == 1) {

			$('.tab').each(function() {
				$(this).toggleClass('active');
			});
			$('.tab-content').each(function() {
				$(this).toggleClass('hidden');
			});
		}
	});

	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}

	function activate(str) {
		$('.tab').each(function() {
			$(this).removeClass('active');
		});
		$('.tab-content').each(function() {
			$(this).addClass('hidden');
		});
		$('#' + str + '').addClass('active');
		var divid = str + '-content';
		$('#' + divid + '').removeClass('hidden');
	}
</script>

</head>
<body>
  <form action="testUpload?${_csrf.parameterName}=${_csrf.token}" method="POST" enctype="multipart/form-data">
     Name  :<input type="text" name="name"/> <br/>
     
     File  : <input type="file" class="file" name="file" />
     
     <br>
     <input type="submit" value="sub"/> 
  </form>

</body>
</html>
