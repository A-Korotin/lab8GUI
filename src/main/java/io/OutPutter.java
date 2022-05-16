package io;
import dragon.Dragon;

import java.util.List;

/**
* Интерфейс вывода информации*/
@Deprecated
public interface OutPutter {

    void output(String msg);

    void output(Dragon dragon);

    <T extends Number>void output(T t);

    List<String> compound();
}
