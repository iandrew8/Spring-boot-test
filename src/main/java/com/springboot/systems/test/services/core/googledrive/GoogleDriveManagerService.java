package com.springboot.systems.test.services.core.googledrive;

import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

public interface GoogleDriveManagerService {

    /**
     * Get an instance of Google Drive
     * @return Drive
     * @throws GeneralSecurityException, GeneralSecurityException
     * @throws IOException, IOException
     */
    Drive getInstance() throws GeneralSecurityException, IOException, URISyntaxException;

    /**
     * Searches for a folder by name under the provided parent folder(parentId)
     * @param parentId, the parent folder id
     * @param folderName, the folder name
     * @param service, the Drive service
     * @return String, the folder id
     * @throws Exception, Exception
     */
    String searchForFolderByName(String parentId, String folderName, Drive service) throws Exception;

    /**
     * Creates a folder under the provided parent folder(parentId) if the folder does not exist
     * @param parentId, the parent folder id
     * @param folderName, the folder name
     * @param driveInstance, the Drive instance
     * @return String, the folder id
     * @throws Exception, Exception
     */
    String findOrCreateFolder(String parentId, String folderName, Drive driveInstance) throws Exception;

    /**
     * Uploads a workbook to Google Drive under the provided folder name
     *
     * @param fileContent, the file content
     * @param folderName,  the folder name
     * @param fileName,    the file name
     */
    void uploadWorkbook(byte[] fileContent, String fileName, String folderName);

    /**
     * Uploads a PDF to Google Drive under the provided folder name
     * @param fileContent, the file content
     * @param fileName, the file name
     * @param folderName, the folder name
     */
    void uploadPDF(byte[] fileContent, String fileName, String folderName);
}
