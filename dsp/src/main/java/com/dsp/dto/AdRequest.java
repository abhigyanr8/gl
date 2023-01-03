package com.dsp.dto;

import javax.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.dsp.model.Ad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="buildAd")


public class AdRequest 
{
	@Min(value=2001, message="Advertiser ID:Advertiser ID Must Be More Then 2001!!!")
	@Max(value=3000, message="Advertiser ID:Advertiser ID Must Be Less Then 3001!!!")
	private int adId;
	@NotBlank(message="AdName cant be Blank")
	private String adName;

	@NotNull(message="AdvertiserId cant be Blank")
	private int advertiserId;


}
