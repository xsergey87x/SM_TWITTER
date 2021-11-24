package sm.diploma.commanOption;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;
import sm.diploma.persistance.jdbc.DbUtils;
import sm.diploma.persistance.memory.TweetDaoMemoryImplementation;
import sm.diploma.persistance.memory.UserDaoImplementation;

import java.sql.*;
import java.time.LocalDate;

public class ConfigurationApp extends Exception {

    private final DaoType daoType;
    private final boolean initDb;
    private final boolean populateDb;

    public ConfigurationApp(DaoType daoType, boolean initDb, boolean populateDb) {
        this.daoType = daoType;
        this.initDb = initDb;
        this.populateDb = populateDb;
    }

    public ConfigurationApp(ConfigurationBuilder builder) {
        this.daoType = builder.daoType;
        this.initDb = builder.initDb;
        this.populateDb = builder.populateDb;
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

    public static class ConfigurationBuilder {

        private DaoType daoType;
        private boolean initDb;
        private boolean populateDb;

        public ConfigurationApp build() {
            if (initDb) {
                DbUtils.initDb(daoType.getValue());
            }
            if (populateDb) {
                DbUtils.populateDb(daoType.getValue());
            }
            return new ConfigurationApp(this);
        }

        public ConfigurationBuilder getDaoType(String daoType) throws Exception {
            if (daoType.equals(DaoType.IN_MEM_Base.getValue())) {
                this.daoType = DaoType.IN_MEM_Base;
            } else if (daoType.equals(DaoType.JDBC.getValue())) {
                this.daoType = DaoType.JDBC;
            } else {
                throw new Exception("Dao type is incorrect");
            }
            return this;
        }

        public ConfigurationBuilder isInitDb(boolean setInitDb) {
            this.initDb = setInitDb;
            return this;
        }

        public ConfigurationBuilder isPopulateDb(boolean populateDb) {
            this.populateDb = populateDb;
            return this;
        }
    }

    public String getDaoType() {
        return daoType.getValue();
    }

    public boolean getInitDb() {
        return initDb;
    }

    public boolean getPopulateDb() {
        return populateDb;
    }

    @Override
    public String toString() {
        return "ConfigurationApp{" +
                "daoType='" + daoType.getValue() + '\'' +
                ", initDb=" + initDb +
                ", populateDb=" + populateDb +
                '}';
    }
}
