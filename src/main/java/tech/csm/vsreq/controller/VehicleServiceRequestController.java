package tech.csm.vsreq.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import tech.csm.vsreq.util.FileUtil;


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
	
	//injecting file util
	@Autowired
	private FileUtil fileUtil;

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
			@RequestParam("file") MultipartFile file,
			RedirectAttributes rd) {
//		run validations first

		if (rs.hasErrors()) {
			rd.addFlashAttribute("validationErrors", rs.getAllErrors());
			return "redirect:/requests/create";

		}
		
		 // Validate  child entities
			
		if (request.getVehicleModel().getModelId() == 0) {
		    rd.addFlashAttribute("error", "Please select a valid vehicle model");
		    return "redirect:/requests/create";
		}

		if (request.getServiceSubType().getServiceSubTypeId() == 0) {
		    rd.addFlashAttribute("error", "Please select a valid service sub type");
		    return "redirect:/requests/create";
		}

	    
		//  Upload file only if present
		if(file != null && !file.isEmpty()) {
	        String contentType = file.getContentType();

			  // check file type
	        if (contentType == null || 
	            !(contentType.equals("image/png") || 
	              contentType.equals("image/jpg") || 
	              contentType.equals("image/jpeg") || 
	              contentType.equals("application/pdf"))) {
	            
	            rd.addFlashAttribute("error", "Only PNG, JPG, JPEG, PDF files allowed");
	            return "redirect:/requests/create";
	        }
	    String uploadedFileName = fileUtil.uploadFile(file);
	    request.setAttachmentPath(uploadedFileName);
		}

//		proceed to save after validations		
		ServiceRequest savedRequest = serviceRequestService.saveRequest(request);
		String msg = savedRequest.getCustomerName() + ", your request is being processed";
		rd.addFlashAttribute("msg", msg);
		return "redirect:/requests/create";

	}
	
//	file download 
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("attachmentPath") String fileName) {

	    try {
	        Path filePath = Paths.get(fileUtil.getDirPath() + fileName);
	        Resource resource = new UrlResource(filePath.toUri());

	        if (!resource.exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=" + resource.getFilename())
	                .body(resource);

	    } catch (Exception e) {
	        throw new RuntimeException("Error downloading file", e);
	    }
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
