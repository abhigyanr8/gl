package com.dsp.controller;
import java.io.IOException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dsp.dto.AdRequest;
import com.dsp.dto.AdvertiserRequest;
import com.dsp.exception.AlreadyExistsException;
import com.dsp.exception.NoSuchElementException;
import com.dsp.model.Ad;
import com.dsp.model.Advertiser;
import com.dsp.service.AdService;
import com.dsp.service.AdvertiserService;
import com.dsp.service.StorageService;

@RestController
// Controller
public class AdvertiserController {
	
	@Autowired //Autowiring Seller Service
	private AdvertiserService advertiserService;
	
	@Autowired //Autowiring Seller Service
	private AdService adService;
	

 

	//to add Advertiser
	@PostMapping("/advertiser")
	public Advertiser add(@RequestBody @Valid AdvertiserRequest advertiserRequest) 
	{
		
		//Exception :if AdvertiserId Already Exists;
	     if(advertiserService.getAdvertiserById(advertiserRequest.getAdvertiserId()).isPresent())
			throw new AlreadyExistsException("Advertiser",advertiserRequest.getAdvertiserId());
		
	     //Exception :if AdvertiserEmail Already Exists
	     if(advertiserService.getAdvertiserByemail(advertiserRequest.getAdvertiserEmail()).isPresent())
	    	 throw new AlreadyExistsException("EmailId already exist",-1);
		
	     //if Advertisers ID or advertiser Email does not Exist in database
		return advertiserService.addAdvertiser(advertiserRequest);
	}
	

	
	//Advertisercontroller //to get advertiserby ID
	@RequestMapping(value = "/advertisers/{Id}", method = RequestMethod.GET, produces = "application/json")
public Optional<Advertiser> getAdvertiserById(@PathVariable("Id") int id)
	{
	    //Exception : if Advertiser is not present in DB.
		
		if(!advertiserService.getAdvertiserById(id).isPresent())
		    throw new NoSuchElementException("Advertiser",id);
		
		//if advertiserId is present
		return advertiserService.getAdvertiserById(id);
	}
	
  //to delete advertiser by ID
	@DeleteMapping( "/advertiser/{id}" )
	public Optional<Advertiser> deleteAdvertiserById(@RequestParam("id") int id) 
	{
		    //Exception : if Advertiser id is not present in DB.
				if(!advertiserService.getAdvertiserById(id).isPresent())
				    throw new NoSuchElementException("Advertiser",id);
				
		//if advertiser id is present in DB.
		Optional<Advertiser> deletedAdvertiser = null;
		Optional<Advertiser> Advertiser = advertiserService.getAdvertiserById(id);	
		advertiserService.deleteAdvertiserBYId(id);
		deletedAdvertiser = Advertiser;
		return deletedAdvertiser;
	} 
	
	
  //To Update Advertiser
	
	@PutMapping( "/advertiser/{id}" )
	public Optional<Advertiser> update(@RequestParam("advertiserId") int advertiserId , @RequestBody @Valid AdvertiserRequest adv)
	{
	    //Exception :if Advertiser id does not exist;
	     if(!advertiserService.getAdvertiserById(advertiserId).isPresent())
			throw new NoSuchElementException("Advertiser",advertiserId);
	     
		   Optional<Advertiser> updatedAdv=advertiserService.updateAdvertiserById(advertiserId,adv);
		       return updatedAdv;
	      
	       
	     
	}

	
}

