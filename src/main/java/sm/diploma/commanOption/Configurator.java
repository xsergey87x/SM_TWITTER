package sm.diploma.commanOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Configurator {

    public static final String DAO_TYPE = "dbType";
    public static final String DB_INIT = "initDb";
    public static final String POPULATE_DB = "populateDb";

    public static ConfigurationApp createConfig(String[] args) {
        Options options = new Options();
        options.addRequiredOption("dbType", DAO_TYPE, true, "source type for JDBC");
        options.addOption("initDb", DB_INIT, false, "flag for initial creating DB");
        options.addOption(POPULATE_DB, false, "flag for filling DB for some data");

        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            ConfigurationApp config = ConfigurationApp.builder()
                    .getDaoType(cmd.getOptionValues(DAO_TYPE))
                    .isInitDb(cmd.hasOption(DB_INIT))
                    .isPopulateDb(cmd.hasOption(POPULATE_DB))
                    .build();

            return config;
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
