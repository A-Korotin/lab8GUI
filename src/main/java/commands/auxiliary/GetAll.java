package commands.auxiliary;

import commands.Command;
import commands.dependencies.Instances;
import exceptions.DatabaseConnectionNotEstablishedException;
import exceptions.DatabaseException;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.ArrayList;
import java.util.List;

public class GetAll extends Command {
    public GetAll() {
        super(new ArrayList<>(List.of("get_all")), 0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        response.elements(instances.dao.getAll());
        return response;
    }
}
