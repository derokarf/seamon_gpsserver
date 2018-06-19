package gpsmon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;
import java.sql.Connection;


public class GpsServerThread extends Thread{

  private static final Logger LOG = LogManager.getLogger(GpsServerThread.class);

  protected DatagramSocket inGpsSocket = null;
  protected boolean next = true;
  private String strPacket = new String();

  public GpsServerThread() throws IOException{
    this("GpsServerThread");
  }
  public GpsServerThread(String name) throws IOException{
    super(name);
    inGpsSocket = new DatagramSocket(9009);
  }

  public void run(){

    LOG.info("GPSMon Server started");

    GpsParser parser = new GpsParser();
    GpsPacket gpsPacket;
    GpsSqlConn dbConn;
    Connection conn;
    GpsSqlSettings dbSettings;
    GpsSqlLoader dbLoader;

    //connect to DB
    dbSettings = new GpsSqlSettings();
    dbConn = new GpsSqlConn(dbSettings);
    if(!dbConn.connect()){
      LOG.error("Couldn't connection to DB, exit");
      return;
    }
    dbLoader = new GpsSqlLoader();

    while(next){
      try{
        byte[] buf = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(buf,buf.length);
        inGpsSocket.receive(inPacket);
        strPacket = new String(buf, Charset.forName("UTF-8"));
        LOG.error(strPacket);
        // if the package look like this
        if (strPacket.length() > 50){
          //parse strPacket
         gpsPacket =  parser.parse(strPacket);
         //and save to base is not null
          if(gpsPacket != null){
            dbLoader.recPacket2Db(gpsPacket,dbConn,"test1");
          }
        }
      } catch (IOException e){
        e.printStackTrace();
        next = false;
      }
    }
    dbConn.close();
    inGpsSocket.close();
  }
}
