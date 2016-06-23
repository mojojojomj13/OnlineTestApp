<!DOCTYPE html>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
</style>

<script type="text/javascript">
	$(document).ready(function() {
		if ($('#successMsgBox').text() != "") {
			$('#successMsgBox').delay(1000).fadeOut(3000);
		}

		if ($('#errMsgBox').text() != "") {
			$('#errMsgBox').delay(1000).fadeOut(3000);
		}
		
		if($('.tab').size() == 1){
		
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
	<c:set var="currentUser" value="${user}" />


	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
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
			<div class="alert alert-info">
				<strong>NOTE:</strong> &nbsp;&nbsp;&nbsp;Welcome to the Admin Home
				screen
			</div>
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
						<li class="tab active" id="tab2"><a
							href="javascript:activate('tab2');"><b> TAB I</b></a></li>
					</sec:authorize>
					<li class="tab" id="tab3"><a
						href="javascript:activate('tab3');"><b> TAB II </b></a></li>
				</ul>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="tab-content" id="tab2-content" style="">
						<fieldset>
							<form id="myform"
								action="${pageContext.request.contextPath}/admin/authorizeClientUsers"
								method="post">
								<table class="tab2 table">
									<tr>
										<th>User Name</th>
										<th>Endpoint Url</th>
										<th width="10%">Authorized</th>
									</tr>

									<tr>
										<td colspan="3">
											<div class="dataCellDiv">
												<table class="dataCell table table-hover">
													<c:forEach items="${users}" var="user">
														<input type="hidden" name="allIdpUsers"
															value="${user.userName}">
														<tr class="ele_rows" id="ele_row_${user.userName}">
															<td style="width: 20%">&nbsp;&nbsp;${user.userName}</td>
															<td style="width: 70%"><input type="text"
																class="form-control" name="endpoint_${user.userName}"
																value="${user.endpoint}"></td>
															<td style="width: 10%;" class="chkbox"><input
																id="chk_${user.userName}" type="checkbox"
																value="${user.userName}" name="selectedUsers"
																style="width: 20px; height: 20px; margin-right: 40px;"
																<c:if test="${user.isAuthenticated()}" >checked="checked"</c:if> /></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<!--############ Spring security required for POST a form data ################-->
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
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

				<sec:authorize access="hasRole('ROLE_STUDENT') OR hasRole('ROLE_ADMIN')">
					<div class="tab-content hidden" id="tab3-content" style="">
						<fieldset>

							<pre>	
		;;lm;lm.lmlnmlknmlknmlknlkn kj lkjn lknlknlknlknlknlknlknlknlk

			jv bkjnlknlknlknnl
</pre>
						</fieldset>
					</div>
				</sec:authorize>


			</div>


		</div>
	</sec:authorize>


</body>
</html>
