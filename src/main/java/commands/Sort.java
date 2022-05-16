package commands;

import collection.Orderable;
import commands.dependencies.Instances;
import io.OutPutter;
import net.Response;

import java.util.List;

/**
 * Класс, предназначенный для сортировки коллекции. Сортировка производится по <i>возрастанию</i> поля <b>"возраст"</b>
 */
public final class Sort extends Command {

    public Sort(List<String> args) {
        super(args, 0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        response.logResult(((Orderable)instances.dao).sort());
        return response;
    }
}