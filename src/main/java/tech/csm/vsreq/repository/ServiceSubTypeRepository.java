package tech.csm.vsreq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.csm.vsreq.model.ServiceSubType;

@Repository
public interface ServiceSubTypeRepository extends JpaRepository<ServiceSubType, Integer> {

	List<ServiceSubType> findByServiceType_ServiceTypeId(Integer serviceTypeId);
	

}
