package commands;

import commands.dependencies.GetProperties;
import commands.dependencies.Instances;
import commands.dependencies.PropertiesDependant;
import dragon.Dragon;
import exceptions.InvalidValueException;
import io.OutPutter;
import io.Properties;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.List;
import java.util.Optional;

/**
 * Класс, предназначенный для добавления элемента в коллекцию, если <b>возраст</b> нового элемента больше возраста всех существующих элементов<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public final class AddIfMax extends Command implements PropertiesDependant {

    public AddIfMax(List<String> args) {
        super(args, 0, 9);
    }

    @Override
    public Response execute(Instances instances) {
        properties.creator_name = user.login;
        Optional<Dragon> maxDragon = instances.dao.getAll().stream().max((d1, d2) -> (int) (d1.getAge() - d2.getAge()));

        long ageMax = maxDragon.isPresent() ? maxDragon.get().getAge() : -1L;

        Response response = new Response();

        if (properties.age > ageMax)
            response.logResult(instances.dao.create(properties));
        else
            response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.INVALID_AGE);

        return response;
    }
}
