<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Leave Application Form</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	crossorigin="anonymous">
</head>
<body>
	<div class="container mt-5">

		<!-- Success Message -->
		<c:if test="${msg ne null}">
			<div id='successAlert' class='alert alert-success' role="alert">
				<span class='font-weight-bold'>Message: </span> ${msg}
			</div>
		</c:if>

		<!-- Error Message -->
		<c:if test="${dateErrorMsg ne null}">
			<div id='errorAlert' class='alert alert-danger' role="alert">
				<span class='font-weight-bold'>Error: </span> ${dateErrorMsg}
			</div>
		</c:if>

		<div class="card">
			<div class="card-header h2 bg-info">Leave Application Form</div>
			<div class="card-body">
				<form id="leaveForm" action="./save-application" method="post">
					<!-- Department Dropdown -->
					<div class="row">
						<div class="col-4 mb-3">
							<label for="departmentId" class="font-weight-bold">Department</label>
							<select id="departmentId" name="departmentId"
								class="form-control" required>
								<option value="">-select-</option>
								<c:forEach items="${departments}" var="d">
									<option value="${d.departmentId}">${d.departmentName}</option>

								</c:forEach>
							</select>
							<div class="invalid-feedback">Please select a department</div>
						</div>

						<!-- Employee Dropdown -->
						<div class="col-4 mb-3">
							<label for="employeeId" class="font-weight-bold">Employee</label>
							<select id="employeeId" name="employee.employeeId"
								class="form-control" required>
								<option value="">-select-</option>
							</select>
							<div class="invalid-feedback">Please select an employee</div>
						</div>

						<!-- Leave Type Dropdown -->
						<div class="col-4 mb-3">
							<label for="leaveTypeId" class="font-weight-bold">Leave
								Type</label> <select id="leaveTypeId" name="leaveType.leaveTypeId"
								class="form-control" required>
								<!-- check on this object things -->
								<option value="">-select-</option>
							</select>
							<div class="invalid-feedback">Please select a leave type</div>
						</div>
					</div>

					<!-- From and To Dates -->
					<div class="row">
						<div class="col-4 mb-3">
							<label for="fromDate" class="font-weight-bold">From Date</label>
							<input type="date" id="fromDate" name="fromDate"
								class="form-control" value="${leaveApplication.fromDate}"
								required>
							<div class="invalid-feedback">Please select start date</div>
						</div>

						<div class="col-4 mb-3">
							<label for="toDate" class="font-weight-bold">To Date</label> <input
								type="date" id="toDate" name="toDate" class="form-control"
								value="${leaveApplication.toDate}" required>
							<div class="invalid-feedback">Please select end date</div>
						</div>
					</div>

					<!-- Reason -->
					<div class="col-8 mb-3">
						<label for="reason" class="font-weight-bold">Reason for
							Leave</label>
						<textarea id="reason" name="reason" rows="4" class="form-control"
							required>${leaveApplication.reason}</textarea>
						<div class="invalid-feedback">Please provide a reason</div>
					</div>

					<div class="text-center mt-3">
						<input type="submit" class="btn btn-success" value="Save">
						<input type="reset" class="btn btn-warning" value="Reset">
					</div>
				</form>


			</div>
		</div>

		<div class="h3 text-warn m-5">
			Click <a href="./get-applied-leaves"> here </a> view applied leaves
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-2.2.4.js"
		crossorigin="anonymous"></script>
	<script>
        // auto hide messages
        document.addEventListener("DOMContentLoaded", function(){
            var al = document.querySelector("#successAlert");
            if(al != null){
                setTimeout(() => { al.remove(); }, 3000);
            }
        });
        
        document.addEventListener("DOMContentLoaded", function(){
            var al = document.querySelector("#errorAlert");
            if(al != null){
                setTimeout(() => { al.remove(); }, 3000);
            }
        });
    </script>



	<script type="text/javascript">
	
	<!--initial real AJAX-->

	$("#departmentId").change(function(e){
		
		$.ajax({
			  url: "http://localhost:8090/emp/employees-by-department-id", //backend endpoint
			  type: "GET",  //GET req-params appended to the url & fetched by #RequestParam
			  data: {
				  departmentId : $(this).val()		     
			  },
			  success: function(response) { //returns JSON response
				  console.log(response)
				  
			      var employeeId=$("#employeeId");  
			      $(employeeId).find("option").remove(); //clears all existing options
			      $(employeeId).append("<option value='0'>-select-</option>") //adds the default select option
				  $(response).each(function(i,e){
					  //loops thru each object in the JSON res, i is index & e is teams object here
					      var fullName = e.firstName + (e.lastName ? " " + e.lastName : "");
					      $(employeeId).append("<option value='"+e.employeeId+"'>"+fullName+"</option>");
			      });
			  },
			  error: function(xhr, status, error) {
			      console.error("Error submitting data: ", error);
			  }
			});
	});

	

$("#employeeId").change(function(e){
		
		$.ajax({
			  url: "http://localhost:8090/emp/leave-types-by-employee-id",
			  type: "GET",
			  data: {
			      employeeId: $(this).val()		     
			  },
			  success: function(response) {
				  console.log(response)
				  
			      var leaveTypeId=$("#leaveTypeId");
			      $(leaveTypeId).find("option").remove();
			      $(leaveTypeId).append("<option value='0'>-select-</option>")
				  $(response).each(function(i,e){
			    	  $(leaveTypeId).append("<option value="+e.leaveTypeId+">"+e.leaveTypeName+"</option>");
			      });
			  },
			  error: function(xhr, status, error) {
			      console.error("Error submitting data: ", error);
			  }
			});
	});
	
	
	<!--Refactored AJAX to handle edits/updates	-->
	<!--
	function loadTeams(departmentId, selectedTeamId, selectedProjectId) {
	    $.ajax({
	        url: "http://localhost:8089/prjct/teams-by-department-id",
	        type: "GET",
	        data: { departmentId: departmentId },
	        success: function(response) {
	            let team = $("#teamId");
	            team.empty().append("<option value='0'>-select-</option>");
	            $(response).each(function(i, t){
	                let selected = t.teamId == selectedTeamId ? "selected" : "";
	                team.append("<option "+selected+" value='"+t.teamId+"'>"+t.teamName+"</option>");
	            });
	            if(selectedTeamId > 0) {
	                loadProjects(selectedTeamId, selectedProjectId);
	            }
	        }
	    });
	}

	function loadProjects(teamId, selectedProjectId) {
	    $.ajax({
	        url: "http://localhost:8089/prjct/projects-by-team-id",
	        type: "GET",
	        data: { teamId: teamId },
	        success: function(response) {
	            let project = $("#projectId");
	            project.empty().append("<option value='0'>-select-</option>");
	            $(response).each(function(i, p){
	                let selected = p.projectId == selectedProjectId ? "selected" : "";
	                project.append("<option "+selected+" value='"+p.projectId+"'>"+p.projectName+"</option>");
	            });
	        }
	    });
	}
	
	<!--Trigger AJAX on page load (for update mode)-->
	<!--
	$(document).ready(function(){
	    var deptId = $("#departmentId").val();
	    var selectedTeamId = "${selectedTeamId}";
	    var selectedProjectId = "${selectedProjectId}";

	    if(deptId > 0){
	        loadTeams(deptId, selectedTeamId, selectedProjectId);
	    }
	});
	
	<!--Keep change events for dynamic updates-->
	<!--
	$("#departmentId").change(function(){
	    loadTeams($(this).val(), 0, 0); // new selection → no pre-selection
	});

	$("#teamId").change(function(){
	    loadProjects($(this).val(), 0); // new selection → no pre-selection
	});


-->

</script>


</body>
</html>