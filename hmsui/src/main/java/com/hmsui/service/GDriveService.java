package com.hmsui.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.hmsui.HmsuiApplication;

@Service
public class GDriveService {
	private static final Logger logger = LoggerFactory.getLogger(GDriveService.class);

	private static final String APPLICATION_NAME = "Google Drive API Java";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
	private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

	@Autowired
	Environment envtConfig;

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = HmsuiApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	public File uploadFile(String fileName, String fileContent) throws IOException, GeneralSecurityException {
		// file on the file system
		InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
		InputStreamContent mediaContent = new InputStreamContent("text/plain", inputStream);

		try {

			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME).build();
			File fileMetadata = new File();
			fileMetadata.setTitle(fileName);
			ParentReference newParent = new ParentReference();
			newParent.setId(envtConfig.getProperty("gdrive.parent.folder.id"));

			fileMetadata.setParents(Collections.singletonList(newParent));

			File file = service.files().insert(fileMetadata, mediaContent).execute();// .setFields("id")

//			PermissionList permissions = service.permissions().list(file.getId()).execute();
//
//			for(Permission rowPermission: permissions.getItems()) {
//				System.out.println(rowPermission.toPrettyString() );
//			}
			com.google.api.services.drive.model.Permission permission = new com.google.api.services.drive.model.Permission();
			permission.setEmailAddress("venkat1003@gmail.com");
			permission.setValue("venkat1003@gmail.com");
			permission.setType("user");
			permission.setRole("reader");
			
			service.permissions()
			.insert(file.getId(), permission)
			.setSendNotificationEmails(true)
			.setEmailMessage("Search Response File shared over mail")
			.execute();

//			PermissionList permissionsresponse = service.permissions().list(file.getId()).execute();
//
//			
//			for(Permission rowPermission: permissionsresponse.getItems()) {
//				System.out.println(rowPermission.toPrettyString() );
//			}
			
			logger.info("File ID:" + file.getId());
			logger.info("getWebContentLink:" + file.getWebContentLink());

			return file;

		} catch (IOException ex) {
			logger.error("Exception: {}", ex.getMessage());
			throw new IOException("Exception: " + ex.getMessage());
		} catch (GeneralSecurityException ex) {
			logger.error("Exception: {}", ex.getMessage());
			throw new GeneralSecurityException("Exception: " + ex.getMessage());
		}
	}

}
