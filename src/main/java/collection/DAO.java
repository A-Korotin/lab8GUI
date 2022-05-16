package collection;

import dragon.Dragon;
import io.Properties;
import net.Response;
import net.codes.EventCode;
import net.codes.Result;

import java.util.List;
/**
* Интерфейс для работы с коллекцией
*/

public interface DAO {
    Result create(Properties properties);
    Result update(int id, Properties properties);
    Result delete(int id);
    Dragon get(int id);
    List<Dragon> getAll();
    Result clear();
}
