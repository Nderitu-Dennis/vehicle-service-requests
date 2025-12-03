package tech.csm.vsreq.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name="manufacturer")
@Getter
@Setter
@ToString


public class Manufacturer implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="manufacturer_id")
	private Integer manufacturerId;
	
	@Column(name="manufacturer_name", nullable=false)
	private String manufacturerName;
	
	@Column(name="created_at", nullable=false)
	private LocalDate createdAt;
	

}
