package com.example.todo.service;

import com.example.todo.dto.UserDTO;
import com.example.todo.entity.User;
import com.example.todo.entity.Test;
import com.example.todo.repository.UsersRepository;
import com.example.todo.repository.FamilymembersRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.file.*;
import java.security.InvalidKeyException;

@Slf4j
@Service
public class StorageUtil {
	// Configure the connection-string with your values
	public static final String storageConnectionString ="DefaultEndpointsProtocol=http;" + "AccountName=your_storage_account_name;" + "AccountKey=your_storage_account_key";
	public static CloudStorageAccount storageAccount = null;
	public static CloudFileClient fileClient  = null;
	public static CloudFileShare share = null;
	
	public StorageUtil(){
	}
	
	public static void init(){
		// Use the CloudStorageAccount object to connect to your storage account
		try {
		    storageAccount = CloudStorageAccount.parse(storageConnectionString);
		    // Create the Azure Files client.
		    fileClient = storageAccount.createCloudFileClient();
		    // Get a reference to the file share
		    share = fileClient.getShareReference("sampleshare");
		    if (share.createIfNotExists()) {
		        System.out.println("New share created");
		    }
		} catch (Exception ex) {
		    log.info("Error creating Azure files client or file share:" + ex.getMessage());
		}		
	}
	
    public static void createDir(String dirName) {
    	if(share == null){
    		init();
    	}
    	try{
    		//Get a reference to the root directory for the share.
    		CloudFileDirectory rootDir = share.getRootDirectoryReference();

    		//Get a reference to the sampledir directory
    		CloudFileDirectory sampleDir = rootDir.getDirectoryReference(dirName);

    		if (sampleDir.createIfNotExists()) {
    			System.out.println("sampledir created");
    		} else {
    			System.out.println("sampledir already exists");
    		}
    	}catch(Exception ex){
    		log.info("Error creating Azure files directories:" + ex.getMessage());
    	}
    }

    public static void uploadFile(String localpageable) {
    	if(share == null){
    		init();
    	}
    	try{
	    	//Get a reference to the root directory for the share.
	    	CloudFileDirectory rootDir = share.getRootDirectoryReference();
	    	// Define the path to a local file.
	    	final String filePath = "C:\\temp\\Readme.txt";
	
	    	CloudFile cloudFile = rootDir.getFileReference("Readme.txt");
	    	cloudFile.uploadFromFile(filePath);
    	}catch(Exception ex){
    		log.info("Error upload a file to Azure:" + ex.getMessage());
    	}
    }
    
    public static void downloadFile(String dirName, String fileName) {
    	if(share == null){
    		init();
    	}
    	try{
	    	//Get a reference to the root directory for the share.
	    	CloudFileDirectory rootDir = share.getRootDirectoryReference();
	
	    	//Get a reference to the directory that contains the file
	    	CloudFileDirectory sampleDir = rootDir.getDirectoryReference(dirName);
	
	    	//Get a reference to the file you want to download
	    	CloudFile file = sampleDir.getFileReference(fileName);
	
	    	//Write the contents of the file to the console.
	    	System.out.println(file.downloadText());
    	}catch(Exception ex){
    		log.info("Error downloading a file from Azure:" + ex.getMessage());
    	}
    }
    
    public static void deleteFile(String dirName, String fileName) {
    	if(share == null){
    		init();
    	}
    	try{
    		// Get a reference to the root directory for the share.
    		CloudFileDirectory rootDir = share.getRootDirectoryReference();

    		// Get a reference to the directory where the file to be deleted is in
    		CloudFileDirectory containerDir = rootDir.getDirectoryReference(dirName);
    	
    		CloudFile file;
    	
    		file = containerDir.getFileReference(fileName);
    		if ( file.deleteIfExists() ) {
    			System.out.println(fileName + " was deleted");
    		}
    	}catch(Exception ex){
    		log.info("Error deleting Azure file:" + ex.getMessage());
    	}
    }
}
