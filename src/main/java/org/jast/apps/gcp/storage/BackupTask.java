package org.jast.apps.gcp.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Component
public class BackupTask {
	
	private final Logger LOG = LoggerFactory.getLogger(BackupTask.class);
	
	private final Storage storage;
	
	@Value("${org.jast.apps.gcp.storage.source-path}")
	private String sourcePath;
	
	@Value("${org.jast.apps.gcp.storage.target-bucket}")
	private String targetBucket;
	
	private BackupTask(Storage storage) {
		this.storage = storage;
	}
	
	@Scheduled(cron = "${org.jast.apps.gcp.storage.backup-cron}")
	public void run() {
		try {
			File sourceDirectory = new File(sourcePath);
			for (File file : sourceDirectory.listFiles()) {
				if (!file.isDirectory()) {
					copyFile(file);
				}
			}
		} catch (IOException ex) {
			LOG.error(ex.getMessage(), ex);
		}
	}
	
	private void copyFile(File file) throws IOException {
		BlobId blobId = BlobId.of(targetBucket, file.getName());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		
		storage.create(blobInfo, Files.readAllBytes(Paths.get(file.getPath())));
		LOG.info("Archivo {} copiado a bucket", file.getName());
		
		file.delete();
	}
	
}
