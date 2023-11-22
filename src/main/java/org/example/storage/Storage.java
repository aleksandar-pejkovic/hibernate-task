package org.example.storage;

import java.util.List;
import java.util.Map;

public interface Storage {

    Map<String, List<?>> getAllData();
}
