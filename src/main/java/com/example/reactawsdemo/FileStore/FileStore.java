package com.example.reactawsdemo.FileStore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

	private final AmazonS3 s3;


	public FileStore(AmazonS3 s3) {
		this.s3 = s3;
	}

	public void save(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream){

		ObjectMetadata metadata = new ObjectMetadata();
		optionalMetaData.ifPresent(stringStringMap -> {
			if(!stringStringMap.isEmpty()){
				stringStringMap.forEach(metadata::addUserMetadata);
			}
		});
		try{
			s3.putObject(path, fileName, inputStream, metadata);
		} catch (AmazonServiceException e) {
			throw new IllegalStateException("Failed to store content to s3", e);
		}
	}
}
