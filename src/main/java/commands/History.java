package commands;

import commands.dependencies.Instances;
import io.OutPutter;
import log.Logger;
import net.Response;
import net.codes.ExitCode;

import java.util.List;
@Deprecated
public final class History extends Command {

    public History(List<String> args) {
        super(args, 0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response().exitCode(ExitCode.OK);
        Logger.getAll().forEach(response::info);
        return response;
    }
}
