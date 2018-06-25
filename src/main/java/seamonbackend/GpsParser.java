package seamonbackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GpsParser {

  private static final Logger LOG = LogManager.getLogger(GpsParser.class);

// 864180036585292;
// imei:864180036585292,tracker,180220122556,,F,042551.000,A,5217.2515,N,10418.0958,E,0.29,298.88;
  private GpsPacket packet;
  private Date tmpTTDD = new Date();
  DateFormat format = new SimpleDateFormat("yyMMddHHmmss");

  public GpsParser(){

  }

  public GpsPacket parse(String str_packet){
    //length of str_packet must be more then 50
    int first_index = 0;
    int second_index = 0;
    String tmpStr;
    //check imei header
    String imei_label = str_packet.substring(0,5);
    if(imei_label.compareToIgnoreCase("imei:") == 0){
      first_index = 5;
      packet = new GpsPacket();
      second_index =  str_packet.indexOf(",",first_index);
      //imei parse and save
      packet.setImei(new Long(str_packet.substring(first_index,second_index)));
      LOG.debug(packet.getImei().toString());
      //validate imei

      //validate type
      first_index = second_index + 1;
      second_index = str_packet.indexOf(",",first_index);
      packet.setType(str_packet.substring(first_index,second_index));
      LOG.debug(packet.getType());

      //ttdd parse and save
      first_index = second_index + 1;
      second_index = str_packet.indexOf(",",first_index);
      if((second_index - first_index) != 12){
        LOG.error("GPS Time Wrong");
        return null;
      }
      tmpTTDD = str2date(str_packet.substring(first_index,second_index));
      if(tmpTTDD != null){
        packet.setTtdd(tmpTTDD.getTime());
        LOG.debug(packet.getTtdd());
      }else{
        LOG.error("GPS Time Wrong");
        return null;
      }

      //skip unknown data
      first_index = str_packet.indexOf("A",second_index);
      //validate gps data
      if (first_index == -1){
        first_index = str_packet.indexOf("V",second_index);
        packet.setValidGPS(false);
      }else{
        packet.setValidGPS(true);
        // imei:864180036585292,tracker,180220122556,,F,042551.000,A,5217.2515,N,10418.0958,E,0.29,298.88;

        //latitude
        first_index = first_index + 2;
        second_index = str_packet.indexOf(",",first_index);
        packet.setLat(new Double(str_packet.substring(first_index,second_index)));
        LOG.debug(packet.getLat());

        //longitude
        first_index = second_index + 3;
        second_index = str_packet.indexOf(",",first_index);
        packet.setLng(new Double(str_packet.substring(first_index,second_index)));
        LOG.debug(packet.getLng());

        //speed
        first_index = second_index + 3;
        second_index = str_packet.indexOf(",",first_index);
        packet.setSpeed(new Double(str_packet.substring(first_index,second_index)));
        LOG.debug(packet.getSpeed());

        //course
        first_index = second_index + 1;
        second_index = str_packet.indexOf(";",first_index);
        packet.setCourse(new Double(str_packet.substring(first_index,second_index)));
        LOG.debug(packet.getCourse());
      }
     return packet;
    }
    return null;
  }

  public Date str2date (String str){
    Date ttdd = null;
    try{
      ttdd = format.parse(str);
    } catch (ParseException e){
      LOG.error("TTDD from packet incorrect");
    }

    return ttdd;
  }
}
