package commands;

import commands.dependencies.CommandCreator;
import commands.dependencies.Instances;
import exceptions.InfiniteLoopException;
import exceptions.InvalidArgsSizeException;
import io.InputReader;
import io.OutPutter;
import net.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, предназначенный для выполнения скрипта (<i>последовательности команд</i>) из файла <br>
 * Путь до искомого файла является обязательным аргументом команды
 */
public final class ExecuteScript extends Command {
    public ExecuteScript(List<String> args) {
        super(args, 1);
    }

    private List<Command> readCommands(Instances instances) {
        String filePath = args.get(0);
        Instances.filePathChain.add(filePath);
        InputReader reader = instances.fileReader;
        reader.addProperties(filePath);
        return CommandCreator.getCommands(reader);
    }
    @Deprecated
    @Override
    public Response execute(Instances instances) {
        if(Instances.filePathChain.contains(args.get(0)))
            throw new InfiniteLoopException();
        List<Command> commands;
        try{
            commands = readCommands(instances);
        } catch(RuntimeException e) {
            return null;
        }

        for (Command c : commands) {
            c.execute(instances);
        }
        return null;
    }

    public List<Command> getNestedCommands(Instances instances) {
        List<Command> output = new ArrayList<>();
        for (Command c : readCommands(instances)) {

            if (c instanceof ExecuteScript script) {
                if (Instances.filePathChain.contains(script.args.get(0)))
                    throw new InfiniteLoopException();
                output.addAll(script.getNestedCommands(instances));
            } else
                output.add(c);

        }

        return output;
    }
}