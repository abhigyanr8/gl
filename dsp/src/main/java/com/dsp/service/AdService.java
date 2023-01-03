package com.dsp.service;


import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dsp.dto.AdRequest;
import com.dsp.model.Ad;
import com.dsp.repository.AdRepository;
import com.dsp.repository.AdvertiserRepository;
import com.dsp.repository.StorageRepository;




//Ad Service Class
@Service
public class AdService 
{
	 //Autowiring Ad Repo
	 @Autowired
	 AdvertiserRepository advertiserRepo;
	 @Autowired
	 AdRepository adRepo;
	 @Autowired
	 StorageRepository storageRepo;
	 
	 //For Adding Ad
	   public Ad addAd(AdRequest adRequest)
	     {
		   Date d = new Date();
		   Ad newAd=Ad.build(adRequest.getAdId(),adRequest.getAdvertiserId(),"NA",adRequest.getAdName(),d,"submitted","No Media File");
		   return adRepo.save(newAd);
	    }
	   
	 //To find all Ad
		public List<Ad>getAllAd()
		  {
			return adRepo.findAll();
		  }
		
	//To find Ad By Id
		public Optional<Ad> getAdvertiserById(int id)
		  {
			return adRepo.findById(id);	
		  }
		
	//To find ad by adId;
		public Optional<Ad> getAdById(int id)
		  {
			return adRepo.findById(id);	
		  }
		
	
    //Delete Ad By  Ad id
		public void deleteAdById(int adId)
		  {
			storageRepo.deleteById(adId);
			adRepo.deleteById(adId);	
		  }
		
		
    //Update Ad By adId
		public Optional<Ad> updateAdById(int adId, AdRequest ad)
		  {
			Optional<Ad> updatedAd=adRepo.findById(adId);
			updatedAd.get().setAdType(ad.getAdName());
	        AdRequest adReq=new AdRequest();
	        adReq.setAdId(updatedAd.get().getAdId());
	        adReq.setAdvertiserId(updatedAd.get().getAdvertiserId());
	        adReq.setAdName(updatedAd.get().getAdName());
	        addAd(adReq);
	        return updatedAd;
		  }
		
		
    /*
     *  Scheduling of activeAds
        Initial Delay 5 mins ,fixed delay 5 mins
    */
		@Scheduled(fixedRate = 300000,initialDelay =300000)   
	     public void activateAds() 
		   {
	              adRepo.activateAds();
	              System.out.println("*");
           }
}
