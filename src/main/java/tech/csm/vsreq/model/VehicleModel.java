package tech.csm.vsreq.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name="model")
@Getter
@Setter
@ToString
public class VehicleModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="model_id")
	private Integer modelId;
	
	@ManyToOne
	@JoinColumn(name="manufacturer_id")
	private Manufacturer manufacturer;  //many models can belong to one manufacturer
	
	@Column(name="model_name")
	private String modelName;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	

}
