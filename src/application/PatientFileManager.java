package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;

public class PatientFileManager {
	/******************************************************************
	 * 
	 * DIRECTORY PATH OPTIONS TO CHOOSE FROM:
	 * 
	 * to save the patient_data directly into the repo directory uncomment first string below but if you run into any permissions
	 * issues, you'll now because it will fail to create the directory for the patient (will run lines 46-48),
	 * then leave current string (lines 30-32) active. 
	 * 
	 * Note: If you change the string used here, then do the same to the string used at the top of the VitalsForm.java file
	 * 
	 ******************************************************************/
	//Directory path to repo LittleSteps directory. Will create /LittleSteps/patient_data if permissions allow
	//private static final String PATIENT_FILE_DIRECTORY = File.separator + "patient_data";
	//Directory path to users /Home/Documents/patient_data folder where patient files are contained 
	private static final String PATIENT_FILE_DIRECTORY = System.getProperty("user.home") + File.separator + "Documents" 
		    + File.separator + "patient_data";
     
    public void createPatientDirectory(String firstName, String lastName, LocalDate dob, String email, String healthIssues, String medications, String pharmacy, String appointment, String notes, String immunizationHistory) {
        //format the entered date of birth to only two digits for month, day, and year
    	String dobFormatted = dob.format(DateTimeFormatter.ofPattern("MMddyy"));
    	//create patient directory in the format firstname_lastnameDOBdigits
        String dirName = String.format("%s_%s%s", firstName.toLowerCase(), lastName.toLowerCase(), dobFormatted);
        File patientDir = new File(PATIENT_FILE_DIRECTORY, dirName);
        
        //create the patient directory, throw an error message if it already exists
        if (!patientDir.exists()) {
            //patientDir.mkdirs();
        	boolean dirCreated = patientDir.mkdirs();
            if (!dirCreated) {
                //directory creation failed
                System.out.println("Failed to create directory: " + patientDir.getPath());
                System.out.println("mkdirs() returns " + dirCreated);
                return;//stop execution if directory wasn't created
            } else
            	System.out.println("Created the following directory succesfully: " + patientDir.getPath());
        }
        else {
        	Alert errorAlert = new Alert(Alert.AlertType.ERROR, "User is already in the system, please use search function to lookup patient file");
            errorAlert.showAndWait();
        }
        
        //Concatenate the contact info into one string to enter into the contact file
        String contactInfo = "First Name: " + firstName + "\n" +
                             "Last Name: " + lastName + "\n" +
                             "DOB: " + dob.format(DateTimeFormatter.ofPattern("MMddyyyy")) + "\n" +  // format (MM-dd--yyyy)
                             "Parent's Email: " + email + "\n";

        //create each file and write the information provided by the nurse
        try {
            Files.write(Paths.get(patientDir.getPath(), "contactInfo.txt"), contactInfo.getBytes());
            Files.write(Paths.get(patientDir.getPath(), "appointment.txt"), appointment.getBytes());
            Files.write(Paths.get(patientDir.getPath(), "medications.txt"), medications.getBytes());
            Files.write(Paths.get(patientDir.getPath(), "immunization.txt"), immunizationHistory.getBytes());
            Files.write(Paths.get(patientDir.getPath(), "notes.txt"), notes.getBytes());
            Files.write(Paths.get(patientDir.getPath(), "healthIssues.txt"), healthIssues.getBytes());
            Files.write(Paths.get(patientDir.getPath(), "pharmacy.txt"), pharmacy.getBytes());
            //emtpy login file that will be filled in once the user creates their login account
            new File(patientDir, "login.txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write file in directory: " + patientDir.getPath());
            System.out.println("Exception message: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Could not create the patient profile directory.").showAndWait();
        }
    }
    
    //method to pull the patient file information from the patient directory
    public Map<String, String> loadPatientProfile(String firstName, String lastName, LocalDate dob) {
    	
        //create the string for the directory name in the way the patient directory is formatted: firstName_lastNameDOBdigits
    	String dobFormatted = dob.format(DateTimeFormatter.ofPattern("MMddyy"));
        String directoryName = String.format("%s_%s%s", firstName.toLowerCase(), lastName.toLowerCase(), dobFormatted);

        //path to the patient's directory
        Path patientDirectory = Paths.get(PATIENT_FILE_DIRECTORY, directoryName);

        //map to hold the patient's information
        Map<String, String> patientInfo = new HashMap<>();

        //retrieve all the patient info if the patient is located, otherwise throw an error message
        if (Files.exists(patientDirectory)) {
            //list of files from the patient's directory
            String[] filesToRead = {"contactInfo.txt", "appointment.txt", "medications.txt", "immunization.txt", "notes.txt", "healthIssues.txt", "pharmacy.txt"};

            //iterate through array of file names
            for (int i = 0; i < filesToRead.length; i++) {
                String fileName = filesToRead[i];
                Path filePath = patientDirectory.resolve(fileName);
                try {
                    //read the content of the file
                    String content = Files.readString(filePath);
                    //store the content in the map with the filename (without extension) as the key
                    patientInfo.put(fileName.substring(0, fileName.indexOf('.')), content);
                } catch (IOException e) {
                    //alert nurse if file doesn't exist or an IO error occurs
                	new Alert(Alert.AlertType.ERROR, "Error loading file.").showAndWait();
                }
            }
        } else {
        	//show message that patient doesn't exist
        	new Alert(Alert.AlertType.ERROR, "Patient directory does not exist: " + patientDirectory).showAndWait();
        }

        return patientInfo;
    }//end of loadPatientProfile
    
    //method to locate patients directory
    public Path findPatientDirectory(String firstName, String lastName, LocalDate dob) {
        File folder = new File(PATIENT_FILE_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        //exit the method if directory is empty or could not retrieve files
        if (listOfFiles == null) 
        	return null;
        
        String dobFormatted = dob.format(DateTimeFormatter.ofPattern("MMddyy"));
        String targetDirName = String.format("%s_%s%s", firstName.toLowerCase(), lastName.toLowerCase(), dobFormatted);

        //iterate through list of files and return the path if found
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isDirectory() && file.getName().equals(targetDirName))
                return file.toPath();
        }
        //after checking all files, if no directory matches, show error message
        new Alert(Alert.AlertType.ERROR, "Directory not found for the patient entered").showAndWait();
        return null; //no directory is found matching the criteria
    }
}
