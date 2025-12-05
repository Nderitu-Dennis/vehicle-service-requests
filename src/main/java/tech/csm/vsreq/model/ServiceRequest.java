package tech.csm.vsreq.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.csm.vsreq.enums.Priority;

@Entity
@Table(name="service_request")
@Getter
@Setter
@ToString

//todo -check more on springboot validations
public class ServiceRequest implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer serviceRequestId;
	
	@NotBlank(message="customer name cannot be blank")
	@Column(name="customer_name")
	private String customerName;
	
	@ManyToOne
	@JoinColumn(name="manufacturer_id")
	private Manufacturer manufacturer;
	
	@ManyToOne
	@JoinColumn(name="model_id")
	private VehicleModel vehicleModel;

	@ManyToOne
	@JoinColumn(name="service_type_id")
	private ServiceType serviceType;
	
	@ManyToOne
	@JoinColumn(name="service_subtype_id")
	private ServiceSubType serviceSubType;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@FutureOrPresent(message="scheduled date cannot be in the past")
	@Column(name="scheduled_date")
	private LocalDate scheduledDate;
	
	@Column(name="attachment_path")
	private String attachmentPath;
	
	@Column(name="created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	
	
	

}
