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
	//Directory path where patient files/directories are contained
    private static final String PATIENT_FILE_DIRECTORY = System.getProperty("user.home") + "/Documents/patient_data";
     
    public void createPatientDirectory(String firstName, String lastName, LocalDate dob, String email, String healthIssues, String medications, String pharmacy, String appointment, String notes, String immunizationHistory) {
        //format the entered date of birth to only two digits for month, day, and year
    	String dobFormatted = dob.format(DateTimeFormatter.ofPattern("MMddyy"));
    	//create patient directory in the format firstname_lastnameDOBdigits
        String dirName = String.format("%s_%s%s", firstName.toLowerCase(), lastName.toLowerCase(), dobFormatted);
        File patientDir = new File(PATIENT_FILE_DIRECTORY, dirName);
        
        //Create the patient directory, throw an error message if it already exists
        if (!patientDir.exists()) {
            patientDir.mkdirs();
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
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Could not create the patient profile directory.");
            errorAlert.showAndWait();
        }
    }
    
    //method to pull the patient file information from the patient directory
    public Map<String, String> loadPatientProfile(String firstName, String lastName, LocalDate dob) {
    	
        //Create the string for the directory name in the way the patient directory is formatted: firstName_lastNameDOBdigits
    	String dobFormatted = dob.format(DateTimeFormatter.ofPattern("MMddyy"));
        String directoryName = String.format("%s_%s%s", firstName.toLowerCase(), lastName.toLowerCase(), dobFormatted);

        //Path to the patient's directory
        Path patientDirectory = Paths.get(PATIENT_FILE_DIRECTORY, directoryName);

        //Map to hold the patient's information
        Map<String, String> patientInfo = new HashMap<>();

        //retrieve all the patient info if the patient is located, otherwise throw an error message
        if (Files.exists(patientDirectory)) {
            //List of files from the patient's directory
            String[] filesToRead = {"contactInfo.txt", "appointment.txt", "medications.txt", "immunization.txt", "notes.txt", "healthIssues.txt", "pharmacy.txt"};

            //Iterate through array of file names
            for (int i = 0; i < filesToRead.length; i++) {
                String fileName = filesToRead[i];
                Path filePath = patientDirectory.resolve(fileName);
                try {
                    //Read the content of the file
                    String content = Files.readString(filePath);
                    //Store the content in the map with the filename (without extension) as the key
                    patientInfo.put(fileName.substring(0, fileName.indexOf('.')), content);
                } catch (IOException e) {
                    //Alert nurse if file doesn't exist or an IO error occurs
                	new Alert(Alert.AlertType.ERROR, "Error loading file.").showAndWait();
                }
            }
        } else {
        	//Show message that patient doesn't exist
        	new Alert(Alert.AlertType.ERROR, "Patient directory does not exist: " + patientDirectory).showAndWait();
        }

        return patientInfo;
    }//end of loadPatientProfile
    
    //method to locate patients directory
    public Path findPatientDirectory(String firstName, String lastName, LocalDate dob) {
        File folder = new File(PATIENT_FILE_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        //Exit the method if directory is empty or could not retrieve files
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
        //After checking all files, if no directory matches, show error message
        new Alert(Alert.AlertType.ERROR, "Directory not found for the patient entered").showAndWait();
        return null; //no directory is found matching the criteria
    }
    
    
    /*****************************f*****************
     * 
     * *******************************************
     * Possible methods for this class are below *
     * *******************************************
     * 
     * could be a:
     *  - method to updateContactInfo to update the contactInfo.txt file
     *  - method to look for and update the healthIssue.txt file
     *  - method to look for and update immunization.txt file
     *  - method to check if patient exists
     * *********************************************/
    
	/*
    //method to save, update, or create patient directory and patient's contactInfo.txt file
    private void saveContactInfo(String patientDirectoryName, String fname, String lname, String dob, String parentsEmail) {

    }//end of saveFile method
    */
}




