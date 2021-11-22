package sm.diploma;

import sm.diploma.commanOption.ConfigurationApp;
import sm.diploma.commanOption.Configurator;
import sm.diploma.model.Tweet;
import sm.diploma.model.User;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.UserDao;
import sm.diploma.persistance.jdbc.TweetDaoJdbcImpl;
import sm.diploma.persistance.jdbc.UserDaoJdbcImpl;

import java.sql.*;
import java.time.LocalDate;

public class Test {

    public static void main(String[] args) throws SQLException {

        ///Configurator configurator = new Configurator();
       ConfigurationApp config =  Configurator.createConfig(args);
       if (config.getInitDb())
       {
           ConfigurationApp.initDb(config.getDaoType());
       }
       if (config.getPopulateDb())
       {
           ConfigurationApp.populateDb(config.getDaoType());
       }

      //  UserDao userDaoJdbc = new UserDaoJdbcImpl();
     //   User firstUser = new User("KEKLOL", "KKK",LocalDate.of(2009, 01, 23), "I am");
     //   TweetDao tweetDaoJdbc = new TweetDaoJdbcImpl();

    //   Tweet tweet = new Tweet(1L, null, LocalDate.now(), "HELLO KEK");
       // System.out.println(tweetDaoJdbc.saveTweet(tweet));
        ///tweetDaoJdbc.deleteTweetById(2L);
      //  System.out.println(tweetDaoJdbc.saveTweet(tweet));
        System.out.println(" ____________________________________ ");
        //System.out.println(userDaoJdbc.getAll());

        //System.out.println("id: " + userList);
    }
}
