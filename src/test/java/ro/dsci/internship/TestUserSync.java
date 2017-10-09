package ro.dsci.internship;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class TestUserSync {
	String locatie = "src/test/resources/CVSTest.csv";
	String locatie2 = "target/CVSTest2.csv";

	@Test
	public void testGabiUserDao() {
		GabrielUserDao userSync = new GabrielUserDao();
		testWithSpecificUserSyncImplementation(userSync);
	}

	@Test
	// citeste useri Rest si scrie useri pe serveri
	public void testGabiForgerockUserDao() {
		UserDao userSync = new GabrielForgerockUserDao();
		UserDao local = new GabrielUserDao();
		List<User> useriLocali = local.readUsers(locatie);

		List<User> usersServerInitial = userSync.readUsers("");
		userSync.writeUsers(useriLocali, "");
		List<User> usersServerFinal = userSync.readUsers("");
		Assert.assertTrue(usersServerFinal.size() == usersServerInitial.size() + useriLocali.size());
	}

	@Test
	public void testVladForgerockUserDao() {
		UserDao userSync = new VladForgeRockUserDao();
		UserDao localUser = new VladUserDao();
		List<User> useriLocali = localUser.readUsers(locatie);

		List<User> usersServerInit = userSync.readUsers("");
		userSync.writeUsers(useriLocali, "");
		List<User> usersServerFin = userSync.readUsers("");
		Assert.assertTrue(usersServerFin.size() == usersServerInit.size() + useriLocali.size());
	}

	@Test
	public void testUnirestForgerockUserDao() {
		UserDao userSync = new UnirestForgeRockUserDao();
		testReadWrite(userSync, 1);
	}

	@Test
	public void testVladUserDao() {
		UserDao userSync = new VladUserDao();
		testWithSpecificUserSyncImplementation(userSync);
	}

	@Test
	public void testAbs() {
		System.out.println(Integer.MIN_VALUE);
		System.out.println(Math.abs(Integer.MIN_VALUE));
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Math.abs(Integer.MAX_VALUE));
		System.out.println(System.currentTimeMillis());
	}

	@Test
	public void testIoanaUserDao() {
		testWithSpecificUserSyncImplementation(new IoanaUserDao());
	}

	private void testWithSpecificUserSyncImplementation(UserDao dao) throws RuntimeException {
		List<User> users = dao.readUsers(locatie);
		Assert.assertEquals(4, users.size());
		Assert.assertEquals("firstuser@gmail.com", users.get(0).email);

		Path p1 = Paths.get(locatie2);
		try {
			Files.deleteIfExists(p1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Assert.assertFalse("everything ok", Files.exists(p1));

		dao.writeUsers(users, locatie2);
		boolean exists = Files.exists(p1);
		Assert.assertTrue("everything ok", exists);
		List<User> actual = dao.readUsers(locatie2);
		Assert.assertEquals(4, actual.size());
		//Assert.assertEquals(users, actual);
	}

	private void testReadWrite(UserDao dao, int size) throws RuntimeException {
		List<User> users = dao.readUsers(locatie);

		System.out.println(Joiner.on("\n").join(users));
		Assert.assertEquals(size, users.size());
	}

	@Test
	public void testReadWrite() {
		UserDao userSync = new UnirestForgeRockUserDao();
		List<User> users = userSync.readUsers("");
		List<User> newUsers = Arrays.asList(new User("88", "username", "first", "last", "email"));
		userSync.writeUsers(newUsers, "");
		List<User> users2 = userSync.readUsers("");
		Assert.assertEquals(users.size() + 1, users2.size());
		users.addAll(newUsers);
		Assert.assertEquals(users, users2);
	}

	@Test
	public void testEqualsBetweenLists() {
		User user1 = new User("02", "username1", "first1", "last1", "email1");
		User user2 = new User("21", "username2", "first2", "last2", "email2");
		List<User> l1 = Arrays.asList(user1, user2);
		List<String> lista = new ArrayList<String>();

		List<User> l2 = Arrays.asList(user1, user2);
		List<String> l3 = Arrays.asList(user1.toString(), user2.toString());
		lista.add(l1.toString());
		lista.add(l2.toString());
		System.out.println(lista.toString());
		Assert.assertEquals(l1, l2);

	}
}
