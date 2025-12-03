<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Leaves application</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>
<body>
	<div class="container mt-5">

		<c:if test="${msg ne null}">
			<div id="alertId" class="alert alert-success">
				<span class="font-weight-bold">Message : </span> ${msg}
			</div>
		</c:if>

		<div class="h3 text-primary mt-5">Leaves Application List</div>


		<table class="table table-bordered table-striped mt-3">
			<thead class="thead-dark">
				<tr>
					<th>Sl.#</th>
					<th>Date Applied</th>
					<th>First Name</th>
					<th>Second Name</th>
					<th>Leave Type</th>
					<th>Days entitled</th>
					<th>From</th>
					<th>To</th>
					<th>Total Days</th>
					<th>Remainder</th>
					<th>Reason</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaves}" var="a" varStatus="counter">
				
					<tr>
						<td>${counter.count}</td>
						<td>${a.appliedOn}</td>

						<td>${a.employee.firstName}</td>
						<td>${a.employee.lastName}</td>
						<td>${a.leaveType.leaveTypeName}</td>
						<td>${entitled[a.leaveApplicationId]}</td>
						
						<td>${a.fromDateFormatted}</td>
						<td>${a.toDateFormatted}</td>
						<td>${a.totalDays}</td>
						<td>${remainder[a.leaveApplicationId]}</td>
						
						<td>${a.reason}</td>
						<td>${a.status}</td>


						<td><a
							href="./delete-leave?leaveApplicationId=${a.leaveApplicationId}"
							class="text-danger">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<div class="h3 text-warn m-5">
		Click <a href="./leave-application-form"> here </a> to apply for a
		leave
	</div>


	<script src="https://code.jquery.com/jquery-2.2.4.js"
		integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
		crossorigin="anonymous"></script>
	<script>
    document.addEventListener("DOMContentLoaded", function(event){
        var al=document.querySelector("#alertId");
        if(al != null){
            setTimeout(() => {
                al.remove();
            }, 3000);
        }			
    });
</script>
</body>
</html>
