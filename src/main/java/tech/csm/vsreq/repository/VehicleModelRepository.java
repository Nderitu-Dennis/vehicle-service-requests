package tech.csm.vsreq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.csm.vsreq.model.Model;
import tech.csm.vsreq.model.VehicleModel;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {

	List<VehicleModel> findByManufacturer_ManufacturerId(Integer manufacturerId);

}
