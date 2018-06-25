package seamonbackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GpsSqlLoader {

  private static final Logger LOG = LogManager.getLogger(GpsSqlLoader.class);
  private GpsSqlConn dbConn;

  public GpsSqlLoader(GpsSqlSettings dbSettings) {
    dbConn = new GpsSqlConn(dbSettings);
  }

  public void close() {
      dbConn.close();
  }

  /**
   * Записывает пакет от трекера в базу
   * @param packet Объект обработанного пакета данных.
   * @return Возвращает <code>true</code> при успешной записи, <code>false</code> в противном случае.
   */
  public boolean recPacket2Db(GpsPacket packet) {
    boolean res = true;

    Statement stGetUroven;
    //create connection to database

      try {
        stGetUroven = dbConn.getConn().createStatement();
        StringBuilder recSqlStr = new StringBuilder("");

        // Выясняем имя таблицы - потсоянная или временная
        if(packet.getIdGadget() > 0) {
          // Трекер зарегистрирован
          recSqlStr.append("INSERT INTO ")
              .append(GpsSqlStatement.TABLE_GPSLIST)
              .append(GpsSqlStatement.LIST_GPS_FIELDS)
              .append(" VALUES ");
          recSqlStr.append(packet.getString2Sql());
        } else {
          // Трекер не зарегистрирован в системе
          recSqlStr.append("INSERT INTO ")
              .append(GpsSqlStatement.TABLE_GPSLIST_NOREG)
              .append(GpsSqlStatement.LIST_GPS_FIELDS_NOREG)
              .append(" VALUES ");
          recSqlStr.append(packet.getString2Sql_noReg());
        }
        // Выполняем запись
        stGetUroven.execute(recSqlStr.toString());
      } catch (SQLException e) {
        res = false;
        LOG.error("Ошибка записи пакета в базу");
        LOG.error(e.getMessage());
      }
    return res;
  }

  /**
   * Загружает список зарегестрированных трекеров из базы
   * @return <code>ArrayList</code> Со списком трекеров
   */
  public List<Gadget> loadGadgets() {
    Statement stGetGadgets;
    ResultSet rsGetGadgets;
    List<Gadget> resList;

    try {
      resList = new ArrayList<Gadget>();
      stGetGadgets = dbConn.getConn().createStatement();
      rsGetGadgets = stGetGadgets.executeQuery(GpsSqlStatement.SQL_GETGADGETS);
      while (rsGetGadgets.next()) {
        Gadget item = new Gadget();
        item.setId(rsGetGadgets.getInt("id"));
        item.setImei(rsGetGadgets.getLong("imei"));
        item.setPhone(rsGetGadgets.getString("phone"));
        item.setType(rsGetGadgets.getString("type"));
        resList.add(item);
      }

    } catch (SQLException e) {
      resList = null;
      LOG.error("Could not load gadgets list");
      LOG.error(e.getMessage());
    }

    return resList;
  }

}
