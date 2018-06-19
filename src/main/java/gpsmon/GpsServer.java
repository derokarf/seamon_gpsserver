package gpsmon;

import java.io.IOException;

public class GpsServer {

  public static void main (String[]  args) throws IOException{

    new GpsServerThread().start();
  }
}
