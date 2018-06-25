package seamonbackend;

public class GpsSqlStatement {
  public static final String LIST_GPS_FIELDS = "(type,gadget,ttdd,lat,lng,speed,course,isvalidgps)";
  public static final String LIST_GPS_FIELDS_NOREG = "(type,imei,ttdd,lat,lng,speed,course,isvalidgps)";
  public static final String TABLE_GADGETS = "gadgets";
  public static final String TABLE_GPSLIST = "gpslist";
  public static final String TABLE_GPSLIST_NOREG = "gpslist_tmp";
  public static final String SQL_GETGADGETS = "SELECT id, imei, phone, name, about, type FROM gadgets";
}
