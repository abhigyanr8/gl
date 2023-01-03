package com.dsp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="buildAdvertiser")
@Entity
@Table(name="Advertiser")
public class Advertiser 
{
	@Id
	private int advertiserId;
	private String advertiserName;
	private String advertiserPhone;
	private String advertiserEmail;
	private String advertiserPassword;
	


}
 