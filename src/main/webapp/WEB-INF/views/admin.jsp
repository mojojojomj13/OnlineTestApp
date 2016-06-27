<!DOCTYPE html>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>
<%@page session="false"%>
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
::-webkit-scrollbar {
	width: 2px;
	height: 5px;
}
body{
    scrollbar-face-width : 2px;
}

@include scrollbars(.2em,slategray)
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
				$(this).removeClass('hidden');
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

	<c:set var="currentUser" value="${user}" />
	<c:out value="${currentUser.quesForm.tabName}"></c:out>


	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" value="${_csrf.token}"
				name="${_csrf.parameterName}" />
		</form>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<div style="float: right;">
				<h3>
					<b>${currentUser.lastName},${currentUser.firstName}</b> | <a
						href="javascript:formSubmit()"> Logout</a>
				</h3>
			</div>
		</c:if>

		<div class="container">
			<h3>User Administration</h3>
			<c:if test="${not empty error}">
				<c:choose>
					<c:when test="${error}">
						<div id="errMsgBox" class="alert alert-danger">
							<strong>Error!</strong> ${msg}.
						</div>
					</c:when>
					<c:otherwise>
						<div id="successMsgBox" class="alert alert-success"
							style="display:;">
							<strong>Success!</strong> ${msg}.
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<div class="tabs" style="width: 100%;">
				<ul class="nav nav-tabs">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li
							class="tab <c:if test='${currentUser.quesForm.tabName == "tab2-content"}'>active</c:if> "
							id="tab2"><a href="javascript:activate('tab2');"><b>
									Admin Screen</b></a></li>
						<li
							class="tab <c:if test='${currentUser.quesForm.tabName == "tab4-content"}'>active</c:if>"
							id="tab4"><a href="javascript:activate('tab4');"><b>
									Questions </b></a></li>
					</sec:authorize>
					<li
						class="tab <c:if test='${currentUser.quesForm.tabName == "tab3-content"}'>active</c:if>"
						id="tab3"><a href="javascript:activate('tab3');"><b>
								Question Paper </b></a></li>
				</ul>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div
						class="tab-content <c:if test='${currentUser.quesForm.tabName != "tab2-content"}'>hidden</c:if> "
						id="tab2-content">
						<fieldset>
							<form id="myform"
								action="${pageContext.request.contextPath}/admin/authorizeClientUsers?${_csrf.parameterName}=${_csrf.token}"
								method="post">
								<input type="hidden" value="${_csrf.token}"
									name="${_csrf.parameterName}" /><input type="hidden"
									name="tabName" value="tab2-content" />

								<table class="tab2 table">
									<tr>
										<th>User Name</th>
										<th>Endpoint Url</th>
										<th width="10%">Authorized</th>
									</tr>

									
									<tr>

										<!--############ Spring security required for POST a form data ################-->
										<th colspan="3"
											style="text-align: center; border-radius: 10px;"><input
											class="btn btn-primary btn-lg btn-block loginBtn"
											type="submit" value="Submit"
											style="border-radius: 5px; width: auto;" /></th>
									</tr>
								</table>
							</form>

						</fieldset>
					</div>
				</sec:authorize>

				<sec:authorize
					access="hasRole('ROLE_STUDENT') OR hasRole('ROLE_ADMIN')">
					<div
						class="tab-content <c:if test='${currentUser.quesForm.tabName != "tab3-content"}'>hidden</c:if>"
						id="tab3-content">
						<fieldset>
							<form action="submitForm" method="POST">
								<input type="hidden" value="${_csrf.token}"
									name="${_csrf.parameterName}" /> <input type="hidden"
									name="tabName" value="tab3-content" /> <b>Current Ques No
									: ${currentUser.quesForm.currentQuesNo}</b>
								<div class="questionBox " style="">
									<c:if
										test="${null != currentUser.quesForm.quesMap[currentUser.quesForm.currentQuesNo].imgSrc}">
										<div class="questionDesc nono-content ">
											<img alt="image here"
												src="data:image/jpg;base64,<c:out value='${currentUser.quesForm.quesMap[currentUser.quesForm.currentQuesNo].imgSrc}'/>"
												width="100%" style="float: left;" />
										</div>
									</c:if>
									<c:if
										test="${null == currentUser.quesForm.quesMap[currentUser.quesForm.currentQuesNo].imgSrc}">
										<div class="questionDesc ">${currentUser.quesForm.quesMap[currentUser.quesForm.currentQuesNo].desc}</div>
									</c:if>
									<input type="hidden" value="${currentUser.quesForm.currentQuesNo}"
										name="currQues" />
									<div class="questionOption ">
										<c:forEach
											items="${currentUser.quesForm.quesMap[currentUser.quesForm.currentQuesNo].ops}"
											var="op" varStatus="status">
											<div style="" class="">
												<b style="color: #cdffff;">${status.count}.</b><br> <input
													type="${currentUser.quesForm.quesMap[currentUser.quesForm.currentQuesNo].inputType}"
													value="${status.count}" name="userAns"
													<c:if test='${op.value}'>checked="checked"</c:if> /> <span
													style="white-space: pre;">${op.key}</span>
											</div>

											<br>
										</c:forEach>
									</div>

								</div>
								<c:forEach items="${currentUser.quesForm.quesMap}" var="ques">
									<div id="quesContainer">
										<c:if test="${ques.key==currentUser.quesForm.currentQuesNo}">
											<input type="submit" value="${ques.key}" name="quesNo"
												class="current" />

										</c:if>
										<c:if
											test="${ques.key!=currentUser.quesForm.currentQuesNo && !ques.value.isAttempted}">
											<input type="submit" value="${ques.key}" name="quesNo"
												class="notcurrent" />

										</c:if>
										<c:if
											test="${ques.key!=currentUser.quesForm.currentQuesNo && ques.value.isAttempted}">
											<input type="submit" value="${ques.key}" name="quesNo"
												class="attempted" />

										</c:if>
									</div>
								</c:forEach>

							</form>
						</fieldset>
					</div>
				</sec:authorize>
			</div>

			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div
					class="tab-content <c:if test='${currentUser.quesForm.tabName != "tab4-content"}'>hidden</c:if> "
					id="tab4-content">
					<fieldset>
						<legend>Create New Questions:</legend>
						<form name="formx" method="POST" action="createQuestion"
							enctype="multipart/form-data">

							<input type="hidden" value="${_csrf.token}"
								name="${_csrf.parameterName}" /><input type="hidden"
								name="tabName" value="tab4-content" />
							<table>
								<tr class="info">
									<td>Question Text</td>
									<td><textarea name="quesText"
											placeholder="enter your question here" required="required"
											rows="20" cols="80">${question.quesText}</textarea>
								</tr>
								<tr class="info">
									<td>Option A</td>
									<td><textarea name="opA" placeholder="option 1 Answer"
											required="required" cols="80">${question.opA}</textarea></td>
								</tr>
								<tr class="info">
									<td>Option B</td>
									<td><textarea name="opB" placeholder="option 2 Answer"
											required="required" cols="80">${question.opB}</textarea></td>
								</tr>
								<tr class="info">
									<td>Option C</td>
									<td><textarea name="opC" placeholder="option 3 Answer"
											required="required" cols="80">${question.opC}</textarea></td>
								</tr>
								<tr class="info">
									<td>Option D</td>
									<td><textarea name="opD" placeholder="option 4 Answer"
											required="required" cols="80">${question.opD}</textarea></td>
								</tr>
								<tr class="info">
									<td>Correct Options</td>
									<td><input type="checkbox" name="correctOps" value="opA"
										<c:if test="${question.correctOps.contains('opA')}">checked="checked"</c:if>>
										A&nbsp;&nbsp;<input type="checkbox" name="correctOps"
										value="opB"
										<c:if test="${question.correctOps.contains('opB')}">checked="checked"</c:if> />
										B&nbsp;&nbsp; <input type="checkbox" name="correctOps"
										value="opC"
										<c:if test="${question.correctOps.contains('opC')}">checked="checked"</c:if> />
										C&nbsp;&nbsp;<input type="checkbox" name="correctOps"
										value="opD"
										<c:if test="${question.correctOps.contains('opD')}">checked="checked"</c:if> />
										D&nbsp;&nbsp;</td>
								</tr>
								<tr class="info">
									<td>marks</td>
									<td><input type="text" pattern="[0-9]*"
										required="required" name="marks" value="${question.marks}"
										size="2"></td>
								</tr>

								<tr class="info">
									<td>Question Image File (Max 1 MB)</td>
									<td><input type="file" class="file" name="file"></td>
								</tr>


							</table>
							<input type="submit" value="create Question" name="action" /> <input
								type="submit" value="update Question" name="action" />
						</form>
					</fieldset>



				</div>
			</sec:authorize>

		</div>
	</sec:authorize>


</body>
</html>
