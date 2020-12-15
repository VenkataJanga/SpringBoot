package com.hmsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hmsapp.entity.PatientEntity;


public interface PatientRepository extends JpaRepository<PatientEntity, String> {

	@Query(value = "SELECT * FROM patientdata WHERE age between ?1 and ?2", nativeQuery = true)
	public List<PatientEntity> findByAgeBetween(int startAge, int endAge);

	@Query(value = "SELECT * FROM patientdata WHERE gender = ?1 and (age between ?2 and ?3)", nativeQuery = true)
	public List<PatientEntity> findByGenderAgeBetween(String gender,int startAge, int endAge);

}
