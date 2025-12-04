package tech.csm.vsreq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.vsreq.model.ServiceType;
import tech.csm.vsreq.repository.ServiceTypeRepository;


@Service
public class ServiceTypeService {
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;
	
	public List<ServiceType> getAllServiceTypes(){
		return serviceTypeRepository.findAll();
		
	}



}
