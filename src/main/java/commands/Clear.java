package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import io.OutPutter;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс, предназначенный для очищения коллекции (<i>безвозвратного удаления всех элементов</i>)
 */
public final class Clear extends Command {

    public Clear(List<String> args) {
        super(args, 0);
    }

    @Override
    public Response execute(Instances instances) {
        int[] idsToDelete = instances.dao.getAll()
                .stream()
                .filter(d -> d.getCreatorName().equals(user.login))
                .mapToInt(Dragon::getId).toArray();
        Arrays.stream(idsToDelete).forEach(instances.dao::delete);

        Response response = new Response().exitCode(ExitCode.OK);

        if (idsToDelete.length == 0)
            response.event(EventCode.NO_ELEMENTS_DELETED);

        return response;
    }
}