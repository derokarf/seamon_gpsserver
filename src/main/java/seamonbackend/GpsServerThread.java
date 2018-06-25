package seamonbackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;


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
    GpsSqlSettings dbSettings;
    GpsSqlLoader dbLoader;

    //connect to DB
    dbSettings = new GpsSqlSettings();
//    dbConn = new GpsSqlConn(dbSettings);
//    if(!dbConn.connect()){
//      LOG.error("Couldn't connection to DB, exit");
//      return;
//    }
    dbLoader = new GpsSqlLoader(dbSettings);

    // Init gadgets scope
    GadgetsScope listGadgets = new GadgetsScope();
    if(listGadgets.initGadgets(dbSettings) == false) {
      LOG.error("Невозможно загрузить список трекеров!");
      return;
    }

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
            // Проверяем регистрацию
            Gadget tmpGadget = listGadgets.getGadget(gpsPacket.getImei());
            if( tmpGadget != null) {
              // Устанавливаем id_gadget, пакет будет записан в постоянную таблицу
              gpsPacket.setIdGadget(tmpGadget.getId());
            }
            dbLoader.recPacket2Db(gpsPacket);
          }
        }
      } catch (IOException e){
        LOG.error(e);
        next = false;
      }
    }
    dbLoader.close();
    inGpsSocket.close();
  }
}
