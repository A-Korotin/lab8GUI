package net.auth;

import commands.Command;
import commands.auxiliary.Login;
import commands.auxiliary.Register;
import exceptions.NonOKExitException;
import net.Client;
import net.Response;
import net.codes.ExitCode;

import java.io.IOException;

public class ServerAuthenticator {

    private final Client client;

    private static final long TIMEOUT = 5L; // sec

    public ServerAuthenticator(String host, int port) throws IOException {
        client = new Client(host, port);
    }

    private boolean sendCommand(Command command) {
        Response response = client.sendCommand(command, TIMEOUT);
        ExitCode exitCode = response.getExitCode();
        if (exitCode != ExitCode.VALID && exitCode != ExitCode.INVALID)
            throw new NonOKExitException(exitCode, response.getEventCodes());


        return exitCode == ExitCode.VALID;
    }

    public boolean isValidUser(User user) {
        Command login = new Login();
        login.user = user;
        return sendCommand(login);
    }

    public boolean registerUser(User user) {
        Command register = new Register();
        register.user = user;
        return sendCommand(register);
    }
}
