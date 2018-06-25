package seamonbackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GpsSqlConn {

  private static final Logger LOG = LogManager.getLogger(GpsSqlConn.class);

  protected Connection dbConn;

  protected GpsSqlSettings dbParam = new GpsSqlSettings();

  public GpsSqlConn(GpsSqlSettings tempDbParam)
  {
    dbParam = tempDbParam; //Запоминаем параметры для подключекния к базе
  }

  public Connection getConn(){
    if(dbConn == null) {
      connect();
    }
    try {
      if (dbConn.isValid(1)) {
        return dbConn;
      } else {
        while(!dbConn.isValid(10)){
          LOG.error("DB Error: lost connect. Try reconnect with a timeout of 10 sec.");
          connect();
        }
      }
    }catch (SQLException e){
      LOG.error("DB Error: " + e.getMessage());
      System.exit(1);
    }
    return dbConn;
  }

  public void close(){
    try {
      if(dbConn != null)
        dbConn.close();
      //LOG.error("Close connection to database");
    } catch (SQLException e) {
      LOG.error(e.getMessage());
      LOG.error("Nothing to close");
    }
  }

  public boolean connect()
  {
    String strConn = "";
    //LOG.error("-------- PostgreSQL JDBC Connection Testing ------------");

    try {

      Class.forName("org.postgresql.Driver");

    } catch (ClassNotFoundException e) {

      LOG.error("Where is your PostgreSQL JDBC Driver? "
          + "Include in your library path!");
      return false;

    }

    //LOG.error("PostgreSQL JDBC Driver Registered!");


    try {
      strConn = "jdbc:postgresql://" + dbParam.getDbHost() + ":" + dbParam.getDbPort() +"/" + dbParam.getDbName();
      dbConn = DriverManager.getConnection(strConn, dbParam.getDbUser(), dbParam.getDbPasswd());
      //LOG.error("Connection to database successful.\n");


    } catch (SQLException e) {

      LOG.error("Connection Failed! Check output console");
      LOG.error("Connect to " + strConn);
      LOG.error(e.getMessage());
      return false;

    }

    if (dbConn != null) {
      //LOG.error("You made it, take control your database now!");
    } else {
      LOG.error("Failed to make connection!");
      return false;
    }
    return	true;
  }
}
