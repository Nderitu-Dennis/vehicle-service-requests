package tech.csm.vsreq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tech.csm.vsreq.model.VehicleModel;
import tech.csm.vsreq.repository.ModelRepository;
import tech.csm.vsreq.repository.VehicleModelRepository;

@Service
public class VehicleModelService {
	
	@Autowired
	private VehicleModelRepository vehicleModelRepository;

	public List<VehicleModel> getModelsByManufacturer(Integer manufacturerId) {
		return vehicleModelRepository.findByManufacturer_ManufacturerId(manufacturerId);
	}
	

  

}
