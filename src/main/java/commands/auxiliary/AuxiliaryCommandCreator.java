package commands.auxiliary;

import commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AuxiliaryCommandCreator {

    @FunctionalInterface
    private interface ConstructorReference {
        Command construct();
    }

    private static final Map<String, ConstructorReference> availableCommands = new HashMap<>();

    static {
        availableCommands.put("login", Login::new);
        availableCommands.put("register", Register::new);
    }

    public static Command getCommand(List<String> args) {
        String name = args.get(0);
        return availableCommands.get(name).construct();
    }
}
