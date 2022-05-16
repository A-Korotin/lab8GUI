package commands.auxiliary;

import commands.Command;
import commands.dependencies.Instances;
import exceptions.UserLoginAlreadyExistsException;
import jdbc.UserManager;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Register extends Command {

    public Register() {
        super(new ArrayList<>(List.of("register")),0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        try {
            UserManager.registerUser(user);
            return response.exitCode(ExitCode.VALID);
        } catch (UserLoginAlreadyExistsException e) {
            return response.exitCode(ExitCode.INVALID).event(EventCode.LOGIN_ALREADY_EXISTS);
        } catch (SQLException e) {
            return response.exitCode(ExitCode.SERVER_ERROR).event(EventCode.DATABASE_ERROR);
        }
    }
}
