package tech.csm.vsreq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tech.csm.vsreq.enums.Priority;
import tech.csm.vsreq.model.ServiceRequest;
import tech.csm.vsreq.model.ServiceSubType;
import tech.csm.vsreq.model.VehicleModel;
import tech.csm.vsreq.service.ManufacturerService;
import tech.csm.vsreq.service.ServiceRequestService;
import tech.csm.vsreq.service.ServiceSubTypeService;
import tech.csm.vsreq.service.ServiceTypeService;
import tech.csm.vsreq.service.VehicleModelService;

/*
•	GET /requests → Show list page
•	GET /requests/create → Create form
•	POST /requests/save → Save form with file upload
•	GET /requests/delete → Delete
•	AJAX endpoints:
o	/requests/models?manufacturerId=
o	/requests/subtypes?serviceTypeId=
*/

@Controller
@RequestMapping("/requests")
public class VehicleServiceRequestController {
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private ServiceTypeService  serviceTypeService;
	
	@Autowired
	private VehicleModelService vehicleModelService;
	
	@Autowired
	private ServiceSubTypeService serviceSubTypeService;
	
	@Autowired
	private ServiceRequestService serviceRequestService;

	// create service request  form
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

   //  Get service sub types by service type id
    @GetMapping("/subtypes")
    @ResponseBody
    public List<ServiceSubType> getServiceSubTypeByServiceType(@RequestParam("serviceTypeId") Integer serviceTypeId) {
        return serviceSubTypeService.getServiceSubTypeByServiceType(serviceTypeId);
    }
    
    //save a request
    @PostMapping("/save")
    public String saveRequest(@ModelAttribute ServiceRequest request, RedirectAttributes rd) {
    	ServiceRequest savedRequest = serviceRequestService.saveRequest(request);
    	String msg =  savedRequest.getCustomerName() + " ,your request is being processed";
    	rd.addFlashAttribute("msg", msg);
    	return "redirect:create";
    	
    	
    }
    
    
}
