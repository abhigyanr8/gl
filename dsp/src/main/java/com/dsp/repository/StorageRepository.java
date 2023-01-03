package com.dsp.repository;

import com.dsp.model.FileData;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<FileData,Integer> {


    Optional<FileData> findByName(String fileName);

	List<FileData> findByAdid(int adid);
}
