package de.Jung.Luciano.Model;

import java.util.List;

public interface SimpleDataInterface {

    /*
    * interface for saving and loading Data
    */

    void save(List<Object> objects, boolean override);

    List<String> load();
}
