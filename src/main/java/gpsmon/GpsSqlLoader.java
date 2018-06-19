package gpsmon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GpsSqlLoader {

  private static final Logger LOG = LogManager.getLogger(GpsSqlLoader.class);

  public boolean recPacket2Db(GpsPacket packet, GpsSqlConn dbConn, String tbName){
    boolean res = true;

    Statement stGetUroven;
    ResultSet rsGetUroven;
    //create connection to database

      try {
        stGetUroven = dbConn.getConn().createStatement();
        StringBuilder recSqlStr = new StringBuilder("");

        int i = 0;
        recSqlStr.append("INSERT INTO ")
            .append(tbName)
            .append(GpsSqlStatement.LIST_GPS_FIELDS)
            .append(" VALUES ");
        recSqlStr.append(packet.getString2Sql());
        LOG.error(recSqlStr.toString());
        stGetUroven.execute(recSqlStr.toString());
      } catch (SQLException e) {
        res = false;
        LOG.error("Record level data in Db is Error");
        LOG.error(e.getMessage());
      }
    return res;
  }
}
