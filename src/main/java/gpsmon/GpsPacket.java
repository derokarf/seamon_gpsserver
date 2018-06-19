package gpsmon;

import java.math.BigInteger;

public class GpsPacket {
  private String type;
  private BigInteger imei;
  private long ttdd;
  private double lat;
  private double lng;
  private double speed;
  private double course;
  private boolean isValidGPS;
//  public static final String LIST_GPS_FIELDS = "(type,imei,ttdd,lat,lng,speed,course,isvalidgps)";

  public String getString2Sql(){
    StringBuilder strSqlFields = new StringBuilder();
    strSqlFields.append("(\'")
        .append(type)
        .append("\',")
        .append(imei)
        .append(",")
        .append(ttdd)
        .append(",")
        .append(lat)
        .append(",")
        .append(lng)
        .append(",")
        .append(speed)
        .append(",")
        .append(course)
        .append(",")
        .append(isValidGPS)
        .append(")");
    return strSqlFields.toString();
  }

  public boolean isValidGPS() {
    return isValidGPS;
  }

  public void setValidGPS(boolean validGPS) {
    isValidGPS = validGPS;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigInteger getImei() {
    return imei;
  }

  public void setImei(BigInteger imei) {
    this.imei = imei;
  }

  public long getTtdd() {
    return ttdd;
  }

  public void setTtdd(long ttdd) {
    this.ttdd = ttdd;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public double getCourse() {
    return course;
  }

  public void setCourse(double course) {
    this.course = course;
  }
}
