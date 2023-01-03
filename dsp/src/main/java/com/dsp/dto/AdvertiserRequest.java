package com.dsp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor(staticName="buildAdvertiser")
@NoArgsConstructor

public class AdvertiserRequest 
{

		@Min(value=1001, message="Advertiser ID:Advertiser ID Must Be More Then 1001!!!")
		@Max(value=2000, message="Advertiser ID:Advertiser ID Must Be Less Then or equal to 2000!!!")
		private int advertiserId;
		@NotBlank(message="Name cant be Blank!!! ")
		private String advertiserName;
		@Pattern(regexp="[6-9][]0-9]{9}",message="Moble number should be 10 digits and start with digit 6,7,8 and 9")
		private String advertiserPhone;
		@NotBlank
		@Email(message=" Email should be in proper format!!!")
		private String advertiserEmail;
		@NotBlank(message="Password cant be Blank!!!")
		private String advertiserPassword;
		

}
