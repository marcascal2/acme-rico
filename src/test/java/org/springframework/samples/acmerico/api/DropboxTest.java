package org.springframework.samples.acmerico.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.acmerico.api.service.DropboxService;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DropboxTest {
	
	@Autowired
	private DropboxService service;
	
	private static Client client = new Client();
	private static User user = new User();
	
	@BeforeAll
	static void setUpUser() {
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);
	}
	
	@BeforeAll
	static void setUpClient(){
		client.setFirstName("TESTS/");
		client.setLastName("Márquez Trujillo");
		client.setAddress("C Marques de Aracena, 37");
		client.setBirthDate(LocalDate.parse("1998-04-15"));
		client.setCity("Sevilla");
		client.setMaritalStatus("Single");
		client.setSalaryPerYear(2000.0);
		client.setAge(21);
		client.setJob("DP2 Developement Student");
		client.setLastEmployDate(LocalDate.parse("2019-04-15"));
		client.setUser(user);
	}
	
	@Test
	public void testUploadFile() throws IOException, DbxException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
		DbxClientV2 dropboxClient=new DbxClientV2(config, "vpE6YdhjRO0AAAAAAAAAyr6r3u2p88ACVUU9mu1KM0MR4nFbFhw2_3_ygih-Qu1Q");
		File fileItem = new File("src/main/resources/static/resources/images/dni-compressed.jpg");
		FileInputStream input = new FileInputStream(fileItem);
		MultipartFile multipartFile = new MockMultipartFile("fileItem",
		            fileItem.getName(), "image/jpeg", input);
		
		this.service.uploadFile(multipartFile, client);
		assertNotNull(dropboxClient.files().getMetadata("/" + client.getFirstName() + " " + client.getLastName() + " - " + client.getAddress() + ".jpg"));
	}
}
