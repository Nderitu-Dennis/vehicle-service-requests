package tech.csm.vsreq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.vsreq.model.ServiceRequest;
import tech.csm.vsreq.repository.ServiceRequestRepository;

@Service
public class ServiceRequestService {
	
	@Autowired
	private ServiceRequestRepository  serviceRequestRepository;

	public ServiceRequest saveRequest(ServiceRequest request) {
		return serviceRequestRepository.save(request);
	}

	public List<ServiceRequest> getAllRequests() {
		return serviceRequestRepository.findAll();
		
	}

	public void deleteRequestById(Integer serviceRequestId) {
		serviceRequestRepository.deleteById(serviceRequestId);	
}

}
