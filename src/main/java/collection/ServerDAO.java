package collection;

import commands.Command;
import commands.auxiliary.Delete;
import commands.auxiliary.GetAll;
import commands.auxiliary.Insert;
import commands.auxiliary.Update;
import dragon.Dragon;
import exceptions.NonOKExitException;
import exceptions.NotImplementedException;
import gui.controller.context.Context;
import io.Properties;
import net.Client;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;
import net.codes.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServerDAO implements DAO {
    private final Client client;

    private static final long TIMEOUT = 5L; //sec

    public ServerDAO(String host, int port) throws IOException {
        client = new Client(host, port);
    }

    @Override
    public Result create(Properties properties) {
        Command insert = new Insert();
        setEssentials(insert, properties);
        return sendCommand(insert);
    }

    @Override
    public Result update(int id, Properties properties) {
        Command update = new Update();
        setEssentials(update, properties, String.valueOf(id));
        return sendCommand(update);
    }

    @Override
    public Result delete(int id) {
        Command delete = new Delete();
        setEssentials(delete, String.valueOf(id));
        return sendCommand(delete);
    }

    @Override
    public Dragon get(int id) {
        Optional<Dragon> dragon = getAll().stream().filter(d -> d.getId() == id).findFirst();

        return dragon.orElse(null);
    }

    @Override
    public List<Dragon> getAll() {
        Command getAll = new GetAll();
        setEssentials(getAll);
        Response response = client.sendCommand(getAll, TIMEOUT);
        if (response.getExitCode() != ExitCode.OK)
            throw new NonOKExitException(response.getExitCode(), response.getEventCodes());
        return response.getElements();
    }

    @Override
    public Result clear() {
        throw new NotImplementedException("No clear allowed");
    }

    private void setEssentials(final Command command, final Properties properties) {
        command.user = Context.user;
        command.setProperties(properties);
    }

    private void setEssentials(final Command command, final Properties properties, final String... args) {
        setEssentials(command, properties);
        if(args.length != 0)
            command.setArgs(new ArrayList<>(List.of(args)));
    }

    private void setEssentials(final Command command, final String... args) {
        command.user = Context.user;
        if (args.length != 0)
            command.setArgs(new ArrayList<>(List.of(args)));
    }

    private Result sendCommand(Command command) {
        Response response = client.sendCommand(command, TIMEOUT);
        ExitCode exitCode = response.getExitCode();
        EventCode eventCode = response.getEventCodes().size() == 0 ? null: response.getEventCodes().get(0); // FIXME: 24.05.2022 only first event code is considered
        String info = response.getInfo();
        return new Result().exitCode(exitCode).eventCode(eventCode).info(info);
    }
}
