package tech.csm.vsreq.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.vsreq.model.Manufacturer;
import tech.csm.vsreq.repository.ManufacturerRepository;

@Service
public class ManufacturerService {
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;

	public List<Manufacturer> getAllManufacturers(){
		return manufacturerRepository.findAll();
	}

}
