

package com.login.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class RentFlatController {

    // Define the base directory for uploaded images for rent listings
    private static final String APP_DATA_DIR_NAME = "MyRealEstateApp";
    private static final String UPLOAD_IMAGES_DIR_NAME = "RentFlatImages"; // CHANGED FOR RENT FLATS
    private Path uploadDirectory;

    private List<String> uploadedImagePaths = new ArrayList<>();

    public RentFlatController() {
        initializeUploadDirectory();
    }

    private void initializeUploadDirectory() {
        String userHome = System.getProperty("user.home");
        uploadDirectory = Paths.get(userHome, APP_DATA_DIR_NAME, UPLOAD_IMAGES_DIR_NAME);

        if (!Files.exists(uploadDirectory)) {
            try {
                Files.createDirectories(uploadDirectory);
                System.out.println("Created upload directory for rent flats: " + uploadDirectory.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error creating upload directory for rent flat: " + e.getMessage());
            }
        } else {
            System.out.println("Upload directory for rent flats already exists: " + uploadDirectory.toAbsolutePath());
        }
    }

    public void handleImageUpload(List<File> selectedFiles) {
        uploadedImagePaths.clear();

        if (uploadDirectory == null || !Files.exists(uploadDirectory)) {
            System.err.println("Upload directory for rent flats is not initialized or does not exist. Cannot save images.");
            return;
        }

        for (File sourceFile : selectedFiles) {
            try {
                String originalFileName = sourceFile.getName();
                String fileExtension = "";
                int dotIndex = originalFileName.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
                    fileExtension = originalFileName.substring(dotIndex);
                }
                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

                Path destinationPath = uploadDirectory.resolve(uniqueFileName);

                Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Rent Flat Image saved: " + destinationPath.toAbsolutePath());
                uploadedImagePaths.add(destinationPath.toString());
            } catch (IOException e) {
                System.err.println("Error saving rent flat image " + sourceFile.getName() + ": " + e.getMessage());
            }
        }
    }

    public List<String> getUploadedImagePaths() {
        return new ArrayList<>(uploadedImagePaths);
    }
}