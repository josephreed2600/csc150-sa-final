package gameoflife.models;

import java.util.ArrayList;

public class CellColorList extends ArrayList<CellColor> {
  public CellColor findByName(String name) {
    for (CellColor cc : this) if (cc.name.equals(name)) return cc;
    return null;
  }
}
