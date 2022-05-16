package commands.auxiliary;

import commands.Command;
import commands.dependencies.Instances;
import exceptions.UserLoginAlreadyExistsException;
import io.OutPutter;
import jdbc.UserManager;
import net.Response;
import net.auth.User;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Login extends Command {


    public Login(){
        super(new ArrayList<>(List.of("login")), 0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        try {
            return response.exitCode(UserManager.isValid(user) ? ExitCode.VALID: ExitCode.INVALID);
        } catch (SQLException e) {
            return response.exitCode(ExitCode.SERVER_ERROR).event(EventCode.DATABASE_ERROR);
        }
    }
}
