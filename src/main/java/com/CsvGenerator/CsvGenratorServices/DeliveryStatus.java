package com.CsvGenerator.CsvGenratorServices;

import java.util.Date;



import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.*;
import lombok.*;




@Entity
@Subselect("select * from delivery_status")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryStatus {
	
	@Id
    public Long id;
	public String createdOn;
	public String destinationNo;
	public String eventDate;
	public String processorResponseId;
	public String sourceNo;
	public String status;
	public boolean processStatus;
	public String cause;
	

}
