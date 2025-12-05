<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>requests</title>
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

		<div class="h3 text-primary mt-5">Vehicle Service Requests List</div>


		<table class="table table-bordered table-striped mt-3">
			<thead class="thead-dark">
				<tr>
					<th>Sl.#</th>
					<th>Customer Name</th>
					<th>Vehicle Manufacturer</th>
					<th>Model</th>
					<th>Service Type</th>
					<th>Sub Type</th>
					<th>Priority</th>
					<th>Scheduled Date</th>
					<th>Vehicle File</th>
					<th>Created On</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requests}" var="a" varStatus="counter">
				
					<tr>
						<td>${counter.count}</td>
						<td>${a.customerName}</td>
						<td>${a.manufacturer.manufacturerName}</td>
						<td>${a.vehicleModel.modelName}</td>
						<td>${a.serviceType.serviceTypeName}</td>
						<td>${a.serviceSubType.serviceSubTypeName}</td>
						<td>${a.priority}</td>
						<td>${a.scheduledDate}</td>						
						<td>${a.attachmentPath}</td>	
						<td>${a.createdAt}</td>


						<td><a
							href="/vsreqs/requests/delete?serviceRequestId=${a.serviceRequestId}"
							class="text-danger">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<div class="h3 text-warn m-5">
		Click <a href="/vsreqs/requests/create"> here </a> to apply for a
		service request
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
