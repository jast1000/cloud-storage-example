package org.jast.apps.gcp.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class StorageConfig {

	@Bean
	public Storage bucketStorage() {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		return storage;
	}
	
}
