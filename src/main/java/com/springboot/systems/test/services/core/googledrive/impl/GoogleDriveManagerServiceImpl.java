package com.springboot.systems.test.services.core.googledrive.impl;

import com.springboot.systems.test.services.core.setup.ApplicationSettingService;
import com.springboot.systems.test.services.core.googledrive.GoogleDriveManagerService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GoogleDriveManagerServiceImpl implements GoogleDriveManagerService {

    private final ApplicationSettingService applicationSettingService;
    private static final String APPLICATION_NAME = "BCS Billing System";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentialsDesktop.json";

    @Override
    public Drive getInstance() throws GeneralSecurityException, IOException, URISyntaxException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, URISyntaxException {
        // Load client secrets.
        InputStream in = GoogleDriveManagerServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(getClass().getClassLoader().getResource(TOKENS_DIRECTORY_PATH).toURI())))
                .setAccessType("offline")
                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost("localhost").setPort(8081).build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setHost("http://bcs-billing-system-backend-staging.ap-northeast-1.elasticbeanstalk.com").setPort(5001).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Override
    public String searchForFolderByName(String parentId, String folderName, Drive service) throws Exception {
        String folderId = null;
        String pageToken = null;
        FileList result = null;

        File fileMetadata = new File();
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setName(folderName);

        do {
            String query = " mimeType = 'application/vnd.google-apps.folder' ";

            if (parentId == null)
                query = query + " and 'root' in parents";
            else
                query = query + " and '" + parentId + "' in parents";

            result = service.files().list()
                    .setIncludeItemsFromAllDrives(true)
                    .setSupportsAllDrives(true)
                    .setQ(query)
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();

            for (File file : result.getFiles()) {

                if (file.getName().equalsIgnoreCase(folderName))
                    folderId = file.getId();
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null && folderId == null);

        return folderId;
    }

    @Override
    public String findOrCreateFolder(String parentId, String folderName, Drive driveInstance) throws Exception {
        String folderId = searchForFolderByName(parentId, folderName, driveInstance);

        // Folder already exists, so return id
        if (folderId != null)
            return folderId;

        //Folder doesn't exist, create it and return folderId
        File fileMetadata = new File();
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setName(folderName);

        if (parentId != null)
            fileMetadata.setParents(Collections.singletonList(parentId));

        return driveInstance.files().create(fileMetadata).setSupportsAllDrives(true)
                .setFields("id")
                .execute()
                .getId();
    }

    @Override
    @Async
    public void uploadWorkbook(byte[] fileContent, String fileName, String folderName) {
        try {
            String folderId = findOrCreateFolder(applicationSettingService.getActiveApplicationSetting().getGoogleDriveFolderId(),
                    folderName, getInstance());

            if (fileContent != null) {
                File fileMetadata = new File();
                fileMetadata.setParents(Collections.singletonList(folderId));
                fileMetadata.setName(fileName);
                fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");
                ByteArrayContent fileContentBytes = new ByteArrayContent(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheetexcel", fileContent);
                File uploadFile = getInstance().files()
                        .create(fileMetadata, fileContentBytes)
                        .setFields("id").execute();
            } else {
                log.error("Error: File content is null");
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }

    @Override
    public void uploadPDF(byte[] fileContent, String fileName, String folderName) {
        try {
            String folderId = findOrCreateFolder(applicationSettingService.getActiveApplicationSetting().getGoogleDriveFolderId(),
                    folderName, getInstance());

            if (fileContent != null) {
                File fileMetadata = new File();
                fileMetadata.setParents(Collections.singletonList(folderId));
                fileMetadata.setName(fileName);
                fileMetadata.setMimeType("application/vnd.google-apps.file");
                ByteArrayContent fileContentBytes = new ByteArrayContent(
                        "application/pdf", fileContent);
                File uploadFile = getInstance().files()
                        .create(fileMetadata, fileContentBytes)
                        .setFields("id").execute();
            } else {
                log.error("Error: File content is null");
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}
