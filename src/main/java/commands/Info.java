package commands;

import collection.Describable;
import com.fasterxml.jackson.core.JsonProcessingException;
import commands.dependencies.Instances;
import exceptions.NotImplementedException;
import io.OutPutter;
import net.Response;
import net.codes.ExitCode;

import java.util.List;

/**
 * Класс, предназначенный для вывода информации о коллекции в ранее заданный поток вывода в формате <b>JSON</b>
 *
 * <table align="left" border="1">
 *     <thead>Формат вывода информации:</thead>
 *     <tr><i>Тип коллекции</i></tr>
 *     <tr><i>Количество элементов в коллекции</i></tr>
 *     <tr><i>Дата инициализации коллекции (с точностью до секунд)</i></tr>
 *     <tr><i>Последний доступный id для элементов коллекции</i></tr>
 *     <tr><i>Элементы коллекции</i></tr>
 * </table>
 */
public final class Info extends Command {

    public Info(List<String> args) {
        super(args, 0);
    }

    @Override
    public Response execute(Instances instances) {
        return new Response()
                .exitCode(ExitCode.OK)
                .info(((Describable)instances.dao).info());
    }
}
