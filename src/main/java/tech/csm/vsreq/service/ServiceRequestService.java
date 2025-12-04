package tech.csm.vsreq.service;

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

}
