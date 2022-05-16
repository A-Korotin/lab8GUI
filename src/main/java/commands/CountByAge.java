package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import io.OutPutter;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.List;

/**
 * Класс, предназначенный для вывода количества элементов с заданным <b>возрастом</b> (<i>обязательный аргумент команды</i>)
 */
public final class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args, 1);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        long age;
        try{
            age = Long.parseLong(args.get(0));
        }
        catch(RuntimeException e){
            response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.INCOMPATIBLE_TYPE);
            return response;
        }
        long ageCount = instances.dao.getAll().stream().filter(dragon -> dragon.getAge().equals(age)).count();

        return response.exitCode(ExitCode.OK).info(ageCount);
    }
}
