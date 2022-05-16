package commands;

import collection.Describable;
import commands.dependencies.Instances;
import exceptions.SavedToTmpFileException;
import io.FileManipulator;
import io.OutPutter;
import net.Response;

import java.util.List;

/**
 * Класс, предназначенный для сохранения информации о коллекции в файл формата <b>JSON</b><br>
 * Путь до искомого файла передается через <i>переменную окружения</i> <b>DAO_COLLECTION_FILEPATH</b>
 */
@Deprecated
public final class Save extends Command {

    public Save(List<String> args) {
        super(args, 0);
    }

    @Override
    public Response execute(Instances instances) {
        try {
            FileManipulator.save(((Describable) instances.dao));
            return null;
        } catch (SavedToTmpFileException e) {
            return null;
        }
        catch (RuntimeException ignored) {}
        return null;

    }
}
