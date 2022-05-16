package commands;

import commands.dependencies.Instances;
import dragon.Dragon;
import io.OutPutter;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.List;
import java.util.Optional;

/**
 * Класс, предназначенный для вывода информации о всех элементах коллекции в формате <b>JSON</b><br>
 *
 * <table align="left" border="1">
 *     <thead>Формат вывода элемента:</thead>
 *     <tr><i>ID</i></tr>
 *     <tr><i>Имя</i></tr>
 *     <tr><i>Координаты</i></tr>
 *     <tr><i>Дата инициализации(с точностью до дней)</i></tr>
 *     <tr><i>Возраст</i></tr>
 *     <tr><i>Цвет</i></tr>
 *     <tr><i>Тип</i></tr>
 *     <tr><i>Характер</i></tr>
 *     <tr><i>Информация о пещере</i></tr>
 * </table>
 */
public final class Show extends Command {

    public Show(List<String> args) {
        super(args, 0);
    }

    public Response execute(Instances instances) {

        Response response = new Response().exitCode(ExitCode.OK);

        if (instances.dao.getAll().size() == 0)
            return response.event(EventCode.EMPTY_COLLECTION);

        instances.dao.getAll().forEach(response::info);

        return response;
    }
}
