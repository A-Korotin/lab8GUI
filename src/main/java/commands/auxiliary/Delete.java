package commands.auxiliary;

import commands.Command;
import commands.dependencies.Instances;
import dragon.Dragon;
import net.Response;
import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.ArrayList;
import java.util.List;

public class Delete extends Command {
    public Delete() {
        super(new ArrayList<>(List.of("delete")), 0);
    }

    @Override
    public Response execute(Instances instances) {
        Response response = new Response();
        int idToDelete = Integer.parseInt(args.get(0));
        String ownerName = instances.dao.get(idToDelete).getCreatorName();
        if (!ownerName.equals(user.login))
            return response.exitCode(ExitCode.INVALID_INPUT).event(EventCode.NO_RIGHTS_TO_MODIFY);

        return response.logResult(instances.dao.delete(idToDelete));
    }
}
