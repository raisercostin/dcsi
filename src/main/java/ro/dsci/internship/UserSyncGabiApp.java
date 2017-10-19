package ro.dsci.internship;

import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class UserSyncGabiApp {
  public static void main(String... args) {
    new UserSyncGabiApp(new GabrielFileUserDao(), new UnirestForgeRockUserDao()).sync(args);
  }
  
  private final UserDao dao;
  private final UnirestForgeRockUserDao adminDao;
  public UserSyncGabiApp(UserDao fileUserDao, UnirestForgeRockUserDao forgeRockUserDao){
    this.dao = fileUserDao;
    this.adminDao = forgeRockUserDao;
  }
  public void sync(String... args) {
    try {
      CommandLine line = new BasicParser().parse(createOptions(), args);
      List<User> tempLocali;
      List<User> tempServer = null;

      if (line.hasOption("csvRead")) {
        String v = line.getOptionValue("csvRead");
        tempLocali = dao.readUsers(v);
      }else{
        throw new RuntimeException("Cannot have csvWrite if csvRead doesn't exist");
      }

      if (line.hasOption("csvWrite")) {
        String v = line.getOptionValue("csvWrite");
        dao.writeUsers(tempLocali, v);
      }

      if (line.hasOption("csvUpdate")) {
        String v = line.getOptionValue("csvUpdate");
        dao.updateUsers(tempLocali, v);
      }
      if (line.hasOption("forgeRockConnect")) {
        String v = line.getOptionValue("forgeRockConnect");
        adminDao.url = v;
      }

      if (line.hasOption("user")) {
        String v = line.getOptionValue("user");
        adminDao.userLogIn = v;
        tempServer =adminDao.readUsers("");
        
      }
      if (line.hasOption("forgerockWritefromServer")) {
          String v = line.getOptionValue("forgerockWritefromServer");
          dao.writeUsers(tempServer, v);
        }


      if (line.hasOption("forgerockWriteOnServer")) {
        String v = line.getOptionValue("forgerockWriteOnServer");
        adminDao.writeUsers(tempLocali, v);
      }

    } catch (ParseException e) {
    	e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  /*
   *  List<User> readUsers(String locatie);
  
  void writeUsers(List<User> users, String locatie);
  
   * 
     //copieze toti userii din fisier.csv in users-all
    UserSyncApp.main("--csv","src/test/resources/CVSTest.csv","--csvWrite","target/users-all.csv");
    //adauge noii user in users-all
    UserSyncApp.main("--csv","src/test/resources/fisier2.csv","--csvWrite","target/users-all.csv");
    //copieze toti userii din fisier.csv in users-all
    UserSyncApp.main("--csv","src/test/resources/fisier.csv","--forgerock","http://localhost:8080","--user","openidm-admin");
    //copieze toti userii din fisier.csv in users-all
    UserSyncApp.main("--forgerock","http://localhost:8080","-
    *
    */

  private static Options createOptions() {
    Options options = new Options();
    options.addOption("csvRead", true, "Citeste CSV din fisier in o lista");
    options.addOption("csvWrite", true, "Scrie CSV din lista in un fisier");
    options.addOption("forgeRockConnect",true,"link");
    options.addOption("csvUpdate", true, "update cu CSV din lista in un fisier/update nu delete");
    options.addOption("forgerockWriteOnServer", true, "Scrie CSV din hard pe server" + "Dati adresa serverului va rog");
    options.addOption("user", true, "Citeste CSV din server in un fisier" + "Dati parola va rog");
    options.addOption("forgerockWritefromServer", true,
        "Scie CSV citit anterior de pe server in un fisier" + "zice-ti unde sa punem CSV");
    return options;
  }

}