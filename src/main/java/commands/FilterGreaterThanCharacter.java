package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import dragon.DragonCharacter;
import io.OutPutter;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.List;

/**
 * Класс, предназначенный для вывода элементов, значение поля <b>характер</b> которых больше заданного (<i>обязательный агрумент команды</i>)<br>
 * Сравнение характеров происходит по <b>длине названия</b> характера
 *
 */
public final class FilterGreaterThanCharacter extends Command {

    public FilterGreaterThanCharacter(List<String> args) {
        super(args, 1);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        DragonCharacter character;
        try {

            character = args.get(0).equalsIgnoreCase("null")? null : DragonCharacter.valueOf(args.get(0).toUpperCase());
        }
        catch (RuntimeException e){
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.INCOMPATIBLE_TYPE);
        }

        long count;
        count = instances.dao.getAll().stream()
                .filter(dragon -> DragonCharacter.compareBoolean(dragon.getCharacter(), character)).count();
        if (count == 0)
            response.event(EventCode.EMPTY_QUERY_RESULT);
        else {
            instances.dao.getAll().stream()
                    .filter(dragon -> DragonCharacter.compareBoolean(dragon.getCharacter(), character))
                    .forEach(response::info);
        }

        return response.exitCode(ExitCode.OK);
    }
}
