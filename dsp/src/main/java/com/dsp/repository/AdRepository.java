package com.dsp.repository;

import java.util.List;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.dsp.model.Ad;
import com.dsp.model.Advertiser;


@Repository
public interface AdRepository extends JpaRepository<Ad, Integer>
{
	List<Ad> findByAdvertiserId(int advertiserId);
    
	@Modifying
	@Transactional
    @Query("UPDATE Ad  SET status = 'active' WHERE TIMESTAMPDIFF(MINUTE,time,now())>=5 AND status ='submitted'")
    public void activateAds();

	
	 
}