package com.hmsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hmsapp.entity.PatientEntity;


public interface PatientRepository extends JpaRepository<PatientEntity, String> {

	@Query(value = "SELECT * FROM patientdata WHERE age between ?1 and ?2", nativeQuery = true)
	public List<PatientEntity> findByAgeBetween(int startAge, int endAge);



	@Query("SELECT p FROM patientdata p WHERE (:gender is null or p.gender = :gender) and  (p.age between :startAge and :endAge) and ( (coalesce(:states, null) is null ) or  p.homeState IN (:states) )  and ( (coalesce(:race, null) is null ) or p.race IN(:race) ) ")
	public List<PatientEntity> findPatientsBySearchParam(@Param("gender") String gender,@Param("startAge") int startAge,@Param("endAge")  int endAge,@Param("states")List<String> states,@Param("race")List<String> race);

}
