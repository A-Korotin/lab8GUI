package commands;


import java.util.List;

import commands.dependencies.Instances;
import exceptions.ProgramExitException;
import io.OutPutter;
import net.Response;

/**
 * Класс, предназначенный для завершения работы программы в штатном режиме (<i>без сохранения изменений в коллекции</i>)
 *
 */

public final class Exit extends Command {

    public Exit(List<String> args) {
        super(args, 0);
    }

    @Override
    public Response execute(Instances instances) {
        throw new ProgramExitException("Завершение программы...");
    }
}
