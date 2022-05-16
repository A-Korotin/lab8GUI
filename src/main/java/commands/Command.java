package commands;


import commands.auxiliary.AuxiliaryCommandCreator;
import commands.dependencies.*;
import exceptions.InvalidArgsSizeException;
import exceptions.InvalidValueException;
import io.OutPutter;
import io.Properties;
import net.Response;
import net.auth.User;

import java.util.Arrays;
import java.util.List;

/**
 * Абстрактный надкласс всех команд
 */

public abstract class Command {
    protected List<String> args;
    protected boolean askForInput;
    protected String name;
    protected Properties properties = null;
    protected int indexShift = 0;
    public User user;

    public void setAskForInput(boolean ask) {
        askForInput = ask;
    }

    public String getName() {
        return name;
    }

    public final CommandProperties getProperties(Instances instances) throws InvalidValueException {
        CommandProperties p = new CommandProperties();
        p.args = args;
        p.args.add(0, name);
        if (this instanceof PropertiesDependant)
            p.properties = GetProperties.getProperties(askForInput, args, instances, indexShift+1);
        return p;
    }

    public static Command restoreFromProperties(CommandProperties properties, User user) {
        List<String> args = properties.args;

        Command c;
        try {
            c = CommandCreator.getCommandDirect(args);
        } catch (NullPointerException e) {
            c = AuxiliaryCommandCreator.getCommand(args);
        }
        c.user = user;
        c.properties = properties.properties;
        return c;
    }

    private boolean validArgsSize(Integer[] expected) {
        return Arrays.stream(expected).anyMatch(i -> i == args.size());
    }

    public Command(List<String> args, Integer... nArgsExpected) {
        this.args = args;
        name = args.get(0);
        args.remove(0);
        args.removeIf(String::isEmpty);

        if (!validArgsSize(nArgsExpected))
            throw new InvalidArgsSizeException(name, args.size(), nArgsExpected);
    }

    /**
     *
     * @param instances класс необходимых экземпляров
     * @return Код выхода команды.
     * <table align="left">
     *     <tr><b>0</b>  - команда завершена <i>без ошибок</i></tr>
     *     <tr><b>-1</b> - команда завершена <i>c ошибками</i></tr>
     * </table>
     *
     */
    public abstract Response execute(Instances instances);
}
