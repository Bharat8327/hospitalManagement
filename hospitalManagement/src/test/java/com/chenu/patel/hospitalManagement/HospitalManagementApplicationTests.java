package com.chenu.patel.hospitalManagement;

import com.chenu.patel.hospitalManagement.dto.ResponseBloodGroupCountEntity;
import com.chenu.patel.hospitalManagement.entity.Patient;
import com.chenu.patel.hospitalManagement.entity.type.BloodGroup;
import com.chenu.patel.hospitalManagement.repository.PatientRepository;
import com.chenu.patel.hospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class HospitalManagementApplicationTests {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PatientService patientService;

	@Test
	public void contextLoads() {
		System.out.println(patientRepository.findAll());
	}

	@Test
	public void testTransaction(){
//		List<Patient> p = patientService.getPatientById(1L);
//		Patient p2 = patientService.findByBirthDateOrEmail();
//		List<Patient> p1 = patientRepository.findByBirthDateBetween(LocalDate.of(1988,01,01),LocalDate.of(1990,01,01));
//		List<Patient> p2 = patientRepository.findByNameContainingOrderByIdDesc("J");
//		List<Patient> p3 = patientRepository.findByBloodGroup(BloodGroup.A_POSITIVE);
//		List<Patient>p4 = patientRepository.findAfterBornDate(LocalDate.of(1990,01,01));
////
//		List<Object[]>p5 = patientRepository.findAllPatientByBloodGroupWithCount();
//		List<Patient> patients = patientRepository.findAllPaitent();
//		int rowaffect = patientRepository.updatedRow(1L,"jon");
//			System.out.println(p2);
//		for (Object[] obj :p5){
//			System.out.println(obj[0]+" "+obj[1]);
//		}
//		System.out.println(rowaffect);
//		List<ResponseBloodGroupCountEntity>bloodCountData = patientRepository.findAllPatientByBloodGroupWithCount();
//		for(ResponseBloodGroupCountEntity bloodGroupCountEntity:bloodCountData){
//			System.out.println(bloodGroupCountEntity);
//		}

		// direct go on offest which page data u require ,these not travel whole page by page , nd this query is optimized query
		// Built into Spring Data JPA.
		Page<Patient> patientsPageList = patientRepository.findAllPatient(PageRequest.of(0, 3 , Sort.by("name","birthDate") ));
		System.out.println(patientsPageList.getTotalPages()); // tell total page
		System.out.println(patientsPageList.getTotalElements()); // total data in database
		System.out.println(patientsPageList.getContent()); // list data of fetch from database
		System.out.println(patientsPageList.getNumberOfElements()); //tell how much data in present page count
		for(Patient patient:patientsPageList){
			System.out.println(patient);
		}
	}

}
