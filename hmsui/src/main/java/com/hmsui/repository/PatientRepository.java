package com.hmsui.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hmsui.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	List<Patient> findByNameLike(String patientName);

	List<Patient> findByDiseaseLike(String disease);

	List<Patient> findByContactNumberLike(String contactNumber);

	@Query(value = "SELECT * FROM patientdata WHERE gender = ?1 and (age between ?2 and ?3)", nativeQuery = true)
	public List<Patient> findByAgeBetween(String gender,int startAge, int endAge);

//	@Query("from patientdata where price between :min and :max")
//	public List<Patient> findByAgeBetween(@Param("min") int min, @Param("max") int max);

}
