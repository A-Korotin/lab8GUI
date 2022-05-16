package collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.NotImplementedException;

/**
 * Интерфейс объектов, для которых можно получить описание в формате JSON
 */
public interface Describable {
    String description() throws JsonProcessingException;

    default String info() {
        throw new NotImplementedException("Метод info не переопределен");
    }
}
