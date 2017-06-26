package ro.dcsi.internship;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import com.opencsv.*;

public class UserManager {
	private final Hashtable<String, User> users = new Hashtable<String, User>();

	public void addUser(User user) {
		users.put(user.username, user);
	}

	public boolean userExists(String username) {
		return users.containsKey(username);
	}

	public User getUser(String username) {
		return new User(users.get(username));
	}

	public int numberOfUsers() {
		return users.size();
	}

	public void importUsers(String csvFile) {
		try {
			CSVReader reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
				User user = new User(line[0], line[1], line[2], line[3], line[4]);
				this.addUser(user);
			}
			reader.close();
		}
		catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void exportUsers(String csvFile) {
	}
}
