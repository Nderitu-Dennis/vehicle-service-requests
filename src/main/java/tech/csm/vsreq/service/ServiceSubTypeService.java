package tech.csm.vsreq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.vsreq.model.ServiceSubType;
import tech.csm.vsreq.model.VehicleModel;
import tech.csm.vsreq.repository.ServiceSubTypeRepository;

@Service
public class ServiceSubTypeService {
	
	@Autowired
	private ServiceSubTypeRepository  serviceSubTypeRepository;

	public List<ServiceSubType> getServiceSubTypeByServiceType(Integer serviceTypeId) {
		return serviceSubTypeRepository.findByServiceType_ServiceTypeId(serviceTypeId);
	}
		
	
	
	

}
