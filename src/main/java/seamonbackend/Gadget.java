package seamonbackend;

public class Gadget {
  private int id;
  private Long imei;
  private String phone;
  private String type;
  private String about;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Long getImei() {
    return imei;
  }

  public void setImei(Long imei) {
    this.imei = imei;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }
}
