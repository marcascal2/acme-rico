package org.springframework.samples.acmerico.apis.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.samples.acmerico.model.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

@Service
public class DropboxService {
	
	private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
	private DbxClientV2 dropboxClient=new DbxClientV2(config, "vpE6YdhjRO0AAAAAAAAAyr6r3u2p88ACVUU9mu1KM0MR4nFbFhw2_3_ygih-Qu1Q");;
	
	@Transactional
	public void uploadFile(MultipartFile file, Client client) throws IOException, DbxException{
		InputStream dni = new ByteArrayInputStream(file.getBytes());
		dropboxClient.files().uploadBuilder("/" + client.getFirstName() + " " + client.getLastName() + ".jpg").uploadAndFinish(dni);
	}
	
}
