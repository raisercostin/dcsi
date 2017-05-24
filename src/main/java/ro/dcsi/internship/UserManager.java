package ro.dcsi.internship;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UserManager {
	public static void main(String[] args) throws IOException {
		String readCsv = "c:\\Users\\Andrei\\maven workspace\\ship1\\src\\main\\resources\\HundredUsers.csv";
		String writeCsv = "c:\\Users\\Andrei\\maven workspace\\ship1\\src\\test\\resources\\WriteHundredUsers.csv";

		BufferedReader br = null;
		FileWriter fr = null;
		String line = "";
		String wordSeparator = ",";
		try {
			br = new BufferedReader(new FileReader(readCsv));
			fr = new FileWriter(writeCsv);
			while ((line = br.readLine()) != null) {
				String[] usersFromCsv = line.split(wordSeparator);
				for (String user : usersFromCsv) {
						System.out.println(user);
						fr.append(user + ",").append("\n");
					}

					
				}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fr.flush();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}

    public static void main2( String[] args )
    {
        List<User> users = readUsers("/home/madalin/Workspace/Eclipse/WORK/UserSyncApp/ship1/src/main/resources/sample.csv");
        for (User user : users) {
        	System.out.println("User " + user/*+ " " + users[1] + " has the email adress: " + users[4]*/);
		}
    }

	public static List<User> readUsers(String csvFile) {
		ArrayList<User> list = new ArrayList<>();
        //String csvFile = "/home/madalin/Workspace/Eclipse/WORK/UserSyncApp/ship1/src/main/resources/sample.csv";
        String line = "";
        String csvSplitBy = ",";
        
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        	boolean firstLine = true;
        	while ((line = br.readLine()) != null){
        		if (!firstLine){
        			String[] users = line.split(csvSplitBy);
            		System.out.println("User " + users[0]+ " " + users[1] + " has the email adress: " + users[4]);
            		list.add(new User(users[0]));
        		} 
        		firstLine = false;
        	}
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}    
		return list;
	}
}
