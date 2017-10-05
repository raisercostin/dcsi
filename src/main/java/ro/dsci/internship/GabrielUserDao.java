package ro.dsci.internship;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GabrielUserDao implements UserDao {

	@Override
	public List<User> readUsers(String locatie) {
		List<User> lista = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(locatie);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);) {
			String line;
			String headerLine = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] userDetails = line.split(",");
				String username = userDetails[0];
				String firstname = userDetails[1];
				String lastname = userDetails[2];
				String email = userDetails[3];
				User user = new User(username, firstname, lastname, email);
				lista.add(user);
				System.out.println(user);
			}

		} catch (Exception e) {
			throw new RuntimeException("Not Implemented Yet!!!");
		}
		return lista;

	}

	@Override
	public void writeUsers(List<User> users, String locatie) {

		try (PrintStream out = new PrintStream(locatie)) {
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				out.print(user.username + "," + user.firstname + "," + user.lastname + "," + user.email + "\n");
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}