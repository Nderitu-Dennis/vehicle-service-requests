package tech.csm.vsreq.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="service_subtype")
@Getter
@Setter
@ToString

public class ServiceSubType implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="service_subtype_id")
	private Integer serviceSubTypeId;
	
	@ManyToOne
	@JoinColumn(name="service_type_id")
	private ServiceType serviceType;  //many sub types belong to one service type
	
	@Column(name="service_subtype_name")
	private String serviceSubTypeName;
	

}
