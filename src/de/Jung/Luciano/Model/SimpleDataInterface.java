package de.Jung.Luciano.Model;

import java.util.List;

public interface SimpleDataInterface {

    void save(List<Object> objects, boolean override);

    List<Object> load();
}
