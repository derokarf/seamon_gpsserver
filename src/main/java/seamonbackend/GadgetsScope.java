package seamonbackend;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class GadgetsScope {
  private List<Gadget> scope;

  /**
   * Добавляет трекер в список
   *
   * @param item Объект трекера
   */

  public void addGadget(Gadget item) {
    scope.add(item);
  }

  /**
   * Ищет в списке нужный трекер по IMEI.
   *
   * @param imei IMEI из UDP пакета
   * @return Возвращает объект трекера, если он есть в списке, в противном случае возвращает <code>null</code>
   */
  public Gadget getGadget(Long imei){
    try {
      return scope.stream()
          .filter(a -> Objects.equals(a.getImei(), imei))
          .findFirst()
          .get();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  /**
   * Загружает в пул и настраивает список трекеров
   * @param dbSettings Параметры подключения к базе данных
   * @return Возвращает <code>true</code> в случае успеха
   */
  public boolean initGadgets(GpsSqlSettings dbSettings) {

    GpsSqlLoader dbLoader = new GpsSqlLoader(dbSettings);
    scope = dbLoader.loadGadgets();
    if(scope == null) {
      return false;
    }
    return true;
  }
}
