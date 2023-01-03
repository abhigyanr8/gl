package com.dsp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsp.dto.AdRequest;
import com.dsp.exception.AlreadyExistsException;
import com.dsp.exception.NoSuchElementException;
import com.dsp.model.Ad;
import com.dsp.model.Advertiser;
import com.dsp.service.AdService;
import com.dsp.service.AdvertiserService;
import com.dsp.service.StorageService;

//Ad Controller
@RestController
public class AdController 

{
	
	@Autowired 
	private AdvertiserService advertiserService;
	
	@Autowired 
	private AdService adService;
	
	
	@Autowired 
	private StorageService storageService;
	
	//to add Ad in database.
		@PostMapping("/Ad")
		public Ad add(@RequestBody  @Valid AdRequest adRequest) 
		   {
			
			//Exception :if AdId Already Exists;
		     if(adService.getAdById(adRequest.getAdId()).isPresent())
				throw new AlreadyExistsException("Ad",adRequest.getAdId());
		     
		     //Exception : if AdvertiserId does not exists;
		     if(!advertiserService.getAdvertiserById(adRequest.getAdvertiserId()).isPresent())
		    	 throw new NoSuchElementException("Advertiser",adRequest.getAdvertiserId());
			
			//if Ad id and advertiserId Exist in database
			return adService.addAd(adRequest);
			
		  }
		
	
	//to delete Ad by Id
		@DeleteMapping( "/Ad/{id}" )
		public Optional<Ad> delete(@RequestParam ("id") int adId) 
		{
			//Exception :if adId does not Exists;
		     if(!adService.getAdById(adId).isPresent())
				throw new NoSuchElementException("Ad",adId);
			
		     //if Ad already Exist in database
			Optional<Ad> deletedAd = null;
			Optional<Ad> ad = adService.getAdById(adId);
			adService.deleteAdById(adId);
			deletedAd = ad;
			return deletedAd;
		}
		
	
		
	//Update Ad by using advertiserid and Adid
			@PutMapping( "/Ad/{id}" )
			public Optional<Ad> update( @RequestParam("adId") int adId
					,@RequestParam("advertiserId") int advertiserId , @RequestBody @Valid AdRequest ad)
			{
				//Exception :if AdId Does not Exists;
			     if(!adService.getAdById(adId).isPresent())
					throw new NoSuchElementException("Ad",adId);
			     
			    //Exception :if Advertiser id does not exist;
			     if(!advertiserService.getAdvertiserById(advertiserId).isPresent())
					throw new NoSuchElementException("Advertiser",advertiserId);
			     
			   //if  AdId and Advertiser ID Exist and Ad belongs to the Advertiser only.
			       if(adService.getAdById(adId).get().getAdvertiserId()==advertiserId)
			          {
				         Optional<Ad> updatedAd=adService.updateAdById(adId,ad);
				         return updatedAd;
			          }
			       
			     //Exception to check whether the Ad belongs to the given Advertiser
			        else  
			          {
			    	     throw new NoSuchElementException("Ad ID does not belongs to the given Advertiser Id",-1);
			          }
			}
        
		//Get Ad by Id
			@RequestMapping(value = "/Ads/{Id}", method = RequestMethod.GET, produces = "application/json")
				public Optional<Ad> get(@PathVariable("Id") int id)
					{
					    //Exception : if Advertiser is not present in DB.
						
						if(!adService.getAdById(id).isPresent())
						    throw new NoSuchElementException("Advertiser",id);
						//if advertiserId is present
						return adService.getAdById(id);
					}

}
