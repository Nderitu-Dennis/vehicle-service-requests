package tech.csm.vsreq.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import tech.csm.vsreq.enums.Priority;
import tech.csm.vsreq.model.ServiceRequest;
import tech.csm.vsreq.model.ServiceSubType;
import tech.csm.vsreq.model.VehicleModel;
import tech.csm.vsreq.service.ManufacturerService;
import tech.csm.vsreq.service.ServiceRequestService;
import tech.csm.vsreq.service.ServiceSubTypeService;
import tech.csm.vsreq.service.ServiceTypeService;
import tech.csm.vsreq.service.VehicleModelService;


@Controller
@RequestMapping("/requests")
public class VehicleServiceRequestController {

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private ServiceTypeService serviceTypeService;

	@Autowired
	private VehicleModelService vehicleModelService;

	@Autowired
	private ServiceSubTypeService serviceSubTypeService;

	@Autowired
	private ServiceRequestService serviceRequestService;

	// create service request form
	@GetMapping("/create")
	public String createServiceRequest(Model model) {
		model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
		model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
		model.addAttribute("priorities", Priority.values());

		return "form";
	}

//	AJAX
	// get models by manufacturer id
	@GetMapping("/models")
	@ResponseBody
	public List<VehicleModel> getVehicleModelsByManufacturer(@RequestParam("manufacturerId") Integer manufacturerId) {
		return vehicleModelService.getModelsByManufacturer(manufacturerId);
	}

	// Get service sub types by service type id
	@GetMapping("/subtypes")
	@ResponseBody
	public List<ServiceSubType> getServiceSubTypeByServiceType(@RequestParam("serviceTypeId") Integer serviceTypeId) {
		return serviceSubTypeService.getServiceSubTypeByServiceType(serviceTypeId);
	}

	// save a request
	@PostMapping("/save")
	public String saveRequest(@Valid @ModelAttribute ServiceRequest request,
			BindingResult rs,
			RedirectAttributes rd) {
//		run validations first

		if (rs.hasErrors()) {
			rd.addFlashAttribute("validationErrors", rs.getAllErrors());
			return "redirect:/requests/create";

		}

//		proceed to save after validations		
		ServiceRequest savedRequest = serviceRequestService.saveRequest(request);
		String msg = savedRequest.getCustomerName() + ", your request is being processed";
		rd.addFlashAttribute("msg", msg);
		return "redirect:/requests/create";

	}

	// get requests list
	@GetMapping("")
	public String getServiceRequests(Model model, RedirectAttributes rd) {
		List<ServiceRequest> requests = serviceRequestService.getAllRequests();

		// if null(no request), return an empty list
		if (requests == null) {
			requests = Collections.emptyList();
			String msg ="nothing to display here";
			rd.addFlashAttribute("msg",msg);
		}

		model.addAttribute("requests", requests);
		return "list";
	}

	// delete =vsreqs/requests/delete/serviceRequestId
	@GetMapping("/delete")
	public String deleteRequest(@RequestParam("serviceRequestId") Integer serviceRequestId,
			RedirectAttributes rd) {
		serviceRequestService.deleteRequestById(serviceRequestId);
		String msg = "Request deleted successfully";
		rd.addFlashAttribute("msg", msg);
		//return "list";
		return "redirect:/requests";
	}

}
