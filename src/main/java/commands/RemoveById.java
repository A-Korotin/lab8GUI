package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import io.OutPutter;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;
import net.codes.Result;

import java.util.List;

/**
 * Класс, предназначенный для удаления элемента коллекции по его <b>ID</b> (<i>обязательный аргумент команды</i>)
 */
public final class RemoveById extends Command {

    public RemoveById(List<String> args) {
        super(args, 1);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        int id;
        try{
            id = Integer.parseInt(args.get(0));
        }
        catch (NumberFormatException e){
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.INCOMPATIBLE_TYPE);
        }

        Dragon toRemove = instances.dao.get(id);
        if(toRemove != null && !toRemove.getCreatorName().equals(user.login))
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.NO_RIGHTS_TO_MODIFY);


        Result result = instances.dao.delete(id);

        return response.logResult(result);

    }
}
