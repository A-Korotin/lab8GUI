package commands;

import commands.dependencies.GetProperties;
import commands.dependencies.Instances;
import commands.dependencies.PropertiesDependant;
import dragon.Dragon;
import exceptions.InvalidValueException;
import io.OutPutter;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.awt.dnd.DropTarget;
import java.util.List;
import java.util.Optional;

/**
 * Класс, предназначенный для обновления существующего элемента по его <b>ID</b> (<i>обязательный аргумент команды</i>)<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public final class Update extends Command implements PropertiesDependant {

    public Update(List<String> args) {
        super(args, 1, 10);
        indexShift = 1;
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

        Optional<Dragon> dragon = instances.dao.getAll().stream().filter(d -> d.getId() == id).findFirst();

        if (dragon.isEmpty())
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.ELEMENT_NOT_FOUND);

        if (!dragon.get().getCreatorName().equals(user.login))
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.NO_RIGHTS_TO_MODIFY);

        response.logResult(instances.dao.update(id, properties));

        return response;
    }
}
