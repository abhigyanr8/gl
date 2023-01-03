package com.dsp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="build")
@Entity
@Table(name="Ad")

public class Ad 
{
	@Id
	private int adId;
	private int advertiserId;
	private String adType;
	private String adName;
	private Date time;
	private String status;
	private String mediaLink;
}
