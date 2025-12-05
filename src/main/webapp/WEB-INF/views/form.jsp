<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Vehicle Service Request Form</title>
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
		
			<!-- validation errors Message -->
		<c:if test="${validationErrors ne null}">
			<div id='errorAlert' class='alert alert-danger' role="alert">
			   <ul>
            <c:forEach var="err" items="${validationErrors}">
               <li>   Error: ${err.defaultMessage}</li>
            </c:forEach>
        </ul>
			</div>
		</c:if>


		<div class="card">
			<div class="card-header h2 bg-info">Vehicle Service Request
				Form</div>
			<div class="card-body">
				<form id="serviceRequestForm"
				 action="/vsreqs/requests/save" 
				 method="post">
					<!-- manufacturer Dropdown -->
					<div class="row">
					
											
					<!-- customer name -->
					<div class="col-4 mb-3">
					<label for="customerName" class="font-weight-bold">Customer Name</label>
					<input type="text" name="customerName" id="customerName" 
					class="form-control"
					 required
					 minlength="3"
					 pattern="[A-Za-z\s]+"
					 title="Must contain only letters and at least 3 characters ">
					</div>

						<div class="col-4 mb-3">
							<label for="manufacturerId" class="font-weight-bold">Manufacturer</label>
							<select id="manufacturerId" name="manufacturer.manufacturerId"
								class="form-control" required>
								<option value="">-select-</option>
								<c:forEach items="${manufacturers}" var="m">
									<option value="${m.manufacturerId}">${m.manufacturerName}</option>

								</c:forEach>
							</select>
							<div class="invalid-feedback">Please select a department</div>
						</div>

						<!-- model Dropdown -->
						<div class="col-4 mb-3">
							<label for="modelId" class="font-weight-bold">Model</label> <select
								id="modelId" name="vehicleModel.modelId" class="form-control" required>
								<option value="">-select-</option>
							</select>
							<div class="invalid-feedback">Please select a model</div>
						</div>
					</div>

					<div class="row">
						<!-- service type Dropdown -->
						<div class="col-6 mb-3">
							<label for="serviceTypeId" class="font-weight-bold">Service
								Type </label> <select id="serviceTypeId"
								name="serviceType.serviceTypeId" class="form-control" required>
								<!-- check on this object things -->
								<option value="">-select-</option>
								<c:forEach items="${serviceTypes}" var="s">
									<option value="${s.serviceTypeId}">${s.serviceTypeName}</option>

								</c:forEach>
							</select>
							<div class="invalid-feedback">Please select service type
								name</div>
						</div>


						<!--service sub type -->
						<div class="col-6 mb-3">
							<label for="serviceSubTypeId" class="font-weight-bold">
								Sub Type</label> <select id="serviceSubTypeId"
								name="serviceSubType.serviceSubTypeId" class="form-control"
								required>
								<option value="">-select-</option>
							</select>
							<div class="invalid-feedback">Please select service sub
								type</div>
						</div>

					</div>

					<div class="row">

						<!-- enum priority -->

						<div class="col-4 mb-3">
							<label for="priority" class="font-weight-bold">Priority</label>
							 <select name="priority" id="priority" class="form-control" required>
								<option value="">-select-</option>
								<c:forEach var="p" items="${priorities}">
s								  <option value="${p}">${p}</option>
								</c:forEach>
							</select>
							<div class="invalid-feedback">Please select a priority</div>
						</div>

						<!-- date-->
						<!--Conditional UI Field: If Priority = "SCHEDULED" → show Date picker
             Otherwise hide it.-->
						<div class="col-4 mb-3" id="scheduledDateWrapper" style="display:none;">
					    <label for="scheduledDate" class="font-weight-bold">Schedule date</label>
							    <input type="date" name="scheduledDate" id="scheduledDate" class="form-control">
						</div>


						<!-- attachment-->

						<div class="col-4 mb-3">
							<label for="attachmentPathId" class="font-weight-bold">Upload
								any vehicle file</label> 
								<input type="file" name=attachmentPathId
								class="form-control" >
							<div class="invalid-feedback">Please upload a file</div>


						</div>
					</div>


					<div class="text-center mt-3">
						<input type="submit" class="btn btn-success" value="Save">
						<input type="reset" class="btn btn-warning" value="Reset">
					</div>
				</form>


			</div>
		</div>


	</div>

	<div class="h3 text-warn m-5">
		Click <a href="/vsreqs/requests"> here </a> to view requests
	</div>
	
	<!-- script for conditional UI if priority='scheduled' show date -->
	<script>
    document.getElementById("priority").addEventListener("change", function () {
        const selected = this.value;
        const dateWrapper = document.getElementById("scheduledDateWrapper");
        const dateInput = document.getElementById("scheduledDate");

        if (selected === "SCHEDULED") {
            dateWrapper.style.display = "block";
            dateInput.required = true;
        } else {
            dateWrapper.style.display = "none";
            dateInput.required = false;
            dateInput.value = "";
        }
    });
</script>
	

	<script src="https://code.jquery.com/jquery-2.2.4.js"
		crossorigin="anonymous"></script>
	<script>
        // auto hide success and error  msgs
        document.addEventListener("DOMContentLoaded", function(){
            var al = document.querySelector("#successAlert");
            if(al != null){
                setTimeout(() => { al.remove(); }, 3000);
            }
        });
        
        document.addEventListener("DOMContentLoaded", function(){
            var al = document.querySelector("#errorAlert");
            if(al != null){
                setTimeout(() => { al.remove(); }, 5000);
            }
        });
    </script>



	<script type="text/javascript">
	
	<!-- AJAX-->

	$("#manufacturerId").change(function(e){
		
		$.ajax({
			  url: "http://localhost:8090/vsreqs/requests/models", //backend endpoint
			  type: "GET",  //GET req-params appended to the url & fetched by #RequestParam
			  data: {
				  manufacturerId : $(this).val()		     
			  },
			  success: function(response) { //returns JSON response
				  console.log(response)
				  
			      var modelId=$("#modelId");  
			      $(modelId).find("option").remove(); //clears all existing options
			      $(modelId).append("<option value='0'>-select-</option>") //adds the default select option
				  $(response).each(function(i,e){
					  //loops thru each object in the JSON res, i is index & e is teams object here
					      $(modelId).append("<option value='"+e.modelId+"'>"+e.modelName+"</option>");
			      });
			  },
			  error: function(xhr, status, error) {
			      console.error("Error submitting data: ", error);
			  }
			});
	});

	

$("#serviceTypeId").change(function(e){
		
		$.ajax({
			  url: "http://localhost:8090/vsreqs/requests/subtypes",
			  type: "GET",
			  data: {
				  serviceTypeId: $(this).val()		     
			  },
			  success: function(response) {
				  console.log(response)
				  
			      var serviceSubTypeId=$("#serviceSubTypeId");
			      $(serviceSubTypeId).find("option").remove();
			      $(serviceSubTypeId).append("<option value='0'>-select-</option>")
				  $(response).each(function(i,e){
			    	  $(serviceSubTypeId).append("<option value="+e.serviceSubTypeId+">"+e.serviceSubTypeName+"</option>");
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
	    loadTeams($(this).val(), 0, 0); // new selection â no pre-selection
	});

	$("#teamId").change(function(){
	    loadProjects($(this).val(), 0); // new selection â no pre-selection
	});


-->

</script>


</body>
</html>