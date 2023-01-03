package com.dsp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.dsp.dto.AdRequest;
import com.dsp.dto.AdvertiserRequest;
import com.dsp.model.Ad;
import com.dsp.model.Advertiser;
import com.dsp.repository.AdRepository;
import com.dsp.repository.AdvertiserRepository;
import com.dsp.repository.StorageRepository;

//Advertiser Service Class
@Service
public class AdvertiserService 
{
	 //Autowiring Advertiser Repo
	
	 @Autowired
	 AdvertiserRepository advertiserRepo;
	 @Autowired
	 AdRepository adRepo;
	 @Autowired
	 StorageRepository storageRepo;
	 
	 //For Adding Advertiser
	 
	   public Advertiser addAdvertiser(AdvertiserRequest advertiserRequest)
	     {
		   Advertiser newAdvertiser=Advertiser.buildAdvertiser(advertiserRequest.getAdvertiserId(), advertiserRequest.getAdvertiserName(), advertiserRequest.getAdvertiserPhone()
					, advertiserRequest.getAdvertiserEmail(), advertiserRequest.getAdvertiserPassword());
		   return advertiserRepo.save(newAdvertiser);
	    }
	   
	 //To find all advertiser
	   
		public List<Advertiser >getAllAdvertiser()
		{
			return advertiserRepo.findAll();
		}
		
	//To find Advertiser By Id
		
		public Optional<Advertiser> getAdvertiserById(int id)
		{
			return advertiserRepo.findById(id);	
		}
		
		
	//To Find Advertiser By advertiserEmail	
		
		public Optional<Advertiser> getAdvertiserByemail(String email)
		{
			return advertiserRepo.findByAdvertiserEmail(email);
		}
		
		
	//Delete Advertiser By Advertiser ID
		
		public void deleteAdvertiserBYId(int advertiserId)
		{
			advertiserRepo.deleteById(advertiserId);
			List<Ad> l1=getAdByAdvertiserId(advertiserId);
			
		//deleting all ads and related media file which is added by advertiser.
			for (int i=0;i<l1.size();i++)
			{
				if(l1.get(i).getAdvertiserId()==advertiserId)
				{
					storageRepo.deleteById(l1.get(i).getAdId());
					adRepo.deleteById(l1.get(i).getAdId());
					
				}
			}
		}

	//Update Advertiser By advertiserId
		
		public Optional<Advertiser> updateAdvertiserById(int advertiserId, AdvertiserRequest adv)
		{
			Optional<Advertiser> updatedAdv=advertiserRepo.findById(advertiserId);
	
			updatedAdv.get().setAdvertiserName(adv.getAdvertiserName());
			updatedAdv.get().setAdvertiserPhone(adv.getAdvertiserPhone());
			updatedAdv.get().setAdvertiserEmail(adv.getAdvertiserEmail());
			updatedAdv.get().setAdvertiserPassword(adv.getAdvertiserPassword());
			
	        AdvertiserRequest advReq=new AdvertiserRequest();

	      
	        advReq.setAdvertiserId(updatedAdv.get().getAdvertiserId());
	        advReq.setAdvertiserName(updatedAdv.get().getAdvertiserName());
	        advReq.setAdvertiserPhone(updatedAdv.get().getAdvertiserPhone());
	        advReq.setAdvertiserEmail(updatedAdv.get().getAdvertiserEmail());
	        advReq.setAdvertiserPassword(updatedAdv.get().getAdvertiserPassword());
	        
	        addAdvertiser(advReq);
	       
	        return updatedAdv;
		}

		
      //to get all Ads by advertiser id
		
		public List<Ad> getAdByAdvertiserId(int advertiserId) 
		   {
			  return adRepo.findByAdvertiserId(advertiserId);				
		   }
	
	 

}
