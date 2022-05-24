package commands.auxiliary;

import commands.Command;
import commands.dependencies.Instances;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.ArrayList;
import java.util.List;

public class Update extends Command {
    public Update() {
        super(new ArrayList<>(List.of("update")),0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        int idToUpdate = Integer.parseInt(args.get(0));
        String ownerName = instances.dao.get(idToUpdate).getCreatorName();
        if(!ownerName.equals(user.login))
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.NO_RIGHTS_TO_MODIFY);
        return response.logResult(instances.dao.update(idToUpdate, properties));
    }
}
