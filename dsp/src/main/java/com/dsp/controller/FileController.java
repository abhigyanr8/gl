package com.dsp.controller;

import java.io.IOException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dsp.exception.NoSuchElementException;
import com.dsp.model.Ad;
import com.dsp.model.FileData;
import com.dsp.repository.AdRepository;
import com.dsp.service.AdService;
import com.dsp.service.AdvertiserService;
import com.dsp.service.StorageService;





@RestController
public class FileController 
{
	
	@Autowired
	private StorageService storageService;
	
	@Autowired 
	private AdService adService;
	
	@Autowired 
	private AdvertiserService advertiserService;
	
	@Autowired
	private AdRepository adRepo;
	
	
	//to add image in db
	
	
	@PostMapping("/file")
	public ResponseEntity<?> uploadImage(@RequestBody @RequestParam("adId") int adId,@RequestParam("file")MultipartFile file) throws IOException, InterruptedException
	  {
		  String uploadImage =storageService.uploadFile(file,adId);	
		  return ResponseEntity.ok().body(uploadImage);		
	  }
	
  	@GetMapping("/file/{adId}")
    public ResponseEntity<?> downloadFile(@PathVariable int adId)
  	    {
  	     //Exception :if adId does not Exists;
	        if(!adService.getAdById(adId).isPresent())
			    throw new NoSuchElementException("Ad",adId);
	        
  		 Ad ad = adService.getAdById(adId).get();
  		 FileData fileDataObj=storageService.getById(adId).get();
         byte[] fileData=storageService.downloadFile(fileDataObj.getName());
    
        if(ad.getStatus().contentEquals("active"))
        {
              if(fileDataObj.getType().contentEquals("video/mp4"))
                {
                        return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.valueOf("video/mp4"))
                        .body(fileData);
                }
              else if(fileDataObj.getType().contentEquals("image/png")||fileDataObj.getType().contentEquals("image/jpeg"))
                 {
        	            return ResponseEntity.status(HttpStatus.OK)
                         .contentType(MediaType.valueOf("image/png"))
                         .body(fileData);
                 }
             else
                 {
        	           return ResponseEntity.status(HttpStatus.OK)
                       .contentType(MediaType.valueOf("audio/wave"))
                       .body(fileData);
                 }
        }
        else
            {
        	    throw new NoSuchElementException("Ad with id "+adId+" is currently not active",-1);
            }
        
        	 
	}
  
        
        @DeleteMapping("/file/{adId}")
        public void deleteFile(@RequestBody @RequestParam("adId") int adId,@RequestParam("advertiserId") int advertiserId)
        {
        	//Exception :if adId does not Exists;
		     if(!adService.getAdById(adId).isPresent())
				throw new NoSuchElementException("Ad",adId);
		     
		     if(!advertiserService.getAdvertiserById(advertiserId).isPresent())
		    	  throw new NoSuchElementException("Advertiser",advertiserId);
		     
		     
		     //if Ad Id and Advertiser ID Exist and Ad belong to the Advertiser only.
		     
		       if(adService.getAdById(adId).get().getAdvertiserId()==advertiserId)
		          {
			
		    		 Optional<Ad> ad = adService.getAdById(adId);
		        	 ad.get().setMediaLink("No Media File");
		             storageService.deleteFile(adId); 
		       
		          }
		       
		     //Exception to check whether the product belongs to the given seller
		       
		        else  
		          {
		    	     throw new NoSuchElementException("Ad ID does not belongs to the given Advertiser Id",-1);
		          }
	  
    }

}
