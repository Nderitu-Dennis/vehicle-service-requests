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
public class Model implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="model_id")
	private Integer modelId;
	
	@ManyToOne
	@JoinColumn(name="manufacturer_id", nullable=false)
	private Manufacturer manufacturer;
	
	@Column(name="model_name", nullable=false)
	private String modelName;
	
	@Column(name="created_at", nullable=false)
	private LocalDate createdAt;
	

}
