package seamonbackend;

public class GpsSqlSettings {
  protected String dbName = "gpsmon_dev"; //database name
  protected String dbHost = "localhost"; //database host
  protected String dbUser = "gpsmon"; //database user
  protected String dbPort = "5432"; //database port
  protected String dbPasswd = "d41d8cd98"; //database password
  protected String dbTableSettings = ""; //name of table with settings for gadget
  protected String dbTableCommand = "";//name of table with command for gadget
  protected String dbTableAddress = "";//name of table with gadget address
  protected String dbTableType = "";//name of table with gadget type


  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getDbHost() {
    return dbHost;
  }

  public void setDbHost(String dbHost) {
    this.dbHost = dbHost;
  }

  public String getDbUser() {
    return dbUser;
  }

  public void setDbUser(String dbUser) {
    this.dbUser = dbUser;
  }

  public String getDbPort() {
    return dbPort;
  }

  public void setDbPort(String dbPort) {
    this.dbPort = dbPort;
  }

  public String getDbPasswd() {
    return dbPasswd;
  }

  public void setDbPasswd(String dbPasswd) {
    this.dbPasswd = dbPasswd;
  }

  public String getDbTableSettings() {
    return dbTableSettings;
  }

  public void setDbTableSettings(String dbTableSettings) {
    this.dbTableSettings = dbTableSettings;
  }

  public String getDbTableCommand() {
    return dbTableCommand;
  }

  public void setDbTableCommand(String dbTableCommand) {
    this.dbTableCommand = dbTableCommand;
  }

  public String getDbTableAddress() {
    return dbTableAddress;
  }

  public void setDbTableAddress(String dbTableAddress) {
    this.dbTableAddress = dbTableAddress;
  }

  public String getDbTableType() {
    return dbTableType;
  }

  public void setDbTableType(String dbTableType) {
    this.dbTableType = dbTableType;
  }
}
