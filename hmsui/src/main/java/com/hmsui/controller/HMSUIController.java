package com.hmsui.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.api.services.drive.model.File;
import com.hmsui.model.SearchParam;
import com.hmsui.service.GDriveService;
import com.hmsui.service.MailService;
import com.hmsui.service.PatientsService;

@Controller
public class HMSUIController {

	@Autowired
	private GDriveService gDriveService;

	@Autowired
	private PatientsService patientsService;

	@Autowired
	private MailService mailService;


	@Autowired
	Environment envtConfig;
	
	@GetMapping("/")
	public String index(Model model) {
		SearchParam searchParam = new SearchParam();
		model.addAttribute("searchparam", searchParam);

		return "index";
	}

	@PostMapping("/submitSearch")
	public String showAllCategories(@ModelAttribute("searchparam") SearchParam searchParam) throws IOException, GeneralSecurityException {
		System.out.println("inside method");
		System.out.println(searchParam.getSearchBy() + searchParam.getSearchText());

		String jsonResposne = patientsService.searchPatients(searchParam);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

	    // pretty print
	    Object jsonObject = mapper.readValue(jsonResposne, Object.class);
	    String prettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
	    
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String fileName = UUID.randomUUID().toString() + "_" + dtf.format(now) + ".json";

		File file = gDriveService.uploadFile(fileName, prettyString);
		
		String mailContent = "Search Response File uploaded to Google Drive successfully.  \n"
							+ "Search Parameters\n Search By :"	+ searchParam.getSearchBy() + " \n Search Text : "+searchParam.getSearchText()+ "\n" 
							+ "File Name :"	+ fileName + "\n" +
							"File Link :" + file.getWebContentLink() + "\n";

//		mailService.sendMail(envtConfig.getProperty("email.fromaddress"),"sardar.nale@gmail.com", "Search Response is Ready", mailContent);
		//to email address -venkat1003@gmail.com

		return "success";

	}

	@GetMapping("/success")
	public String success() {
		return "success";
	}
}
