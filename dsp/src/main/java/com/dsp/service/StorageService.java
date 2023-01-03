package com.dsp.service;

import com.dsp.model.Ad;
import com.dsp.model.FileData;
import com.dsp.repository.AdRepository;
import com.dsp.repository.StorageRepository;
import com.dsp.util.FileUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepo;
    
    @Autowired
    private AdRepository adRepo;

    public String uploadFile(MultipartFile file,int adId) throws IOException 
    {
    	FileData fileData = storageRepo.save(FileData.builder()
                .name(file.getOriginalFilename()).adid(adId)
                .type(file.getContentType())
                .fileData(FileUtils.compressFile(file.getBytes())).build());
    	
    	Optional<Ad> findAd=adRepo.findById(fileData.getAdid());
	    	 Ad ad=findAd.get();
	    	 ad.setAdType(file.getContentType());
	    	 ad.setMediaLink("localhost:8080/file/"+adId);
    	    adRepo.save(ad);
        if (fileData != null) 
            {
                return "file uploaded successfully : " + file.getOriginalFilename();
            }
        return null;
    }

    public byte[] downloadFile(String fileName)
    {
    
           Optional<FileData> dbImageData = storageRepo.findByName(fileName);
           byte[] images=FileUtils.decompressFile(dbImageData.get().getFileData());
           return images;
    }
    
   public Optional<FileData> getById(int adId)
      {
	     return storageRepo.findById(adId);
      }
    
    public void deleteFile(int adId)
    {
    	storageRepo.deleteById(adId);
    	
    }
}
