package com.dsp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsp.model.Advertiser;


@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, Integer>
{
		Optional<Advertiser> findByAdvertiserEmail(String email);

}
