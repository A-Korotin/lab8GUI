package commands.auxiliary;

import commands.Command;
import commands.dependencies.Instances;
import net.Response;

import java.util.ArrayList;
import java.util.List;

public class Insert extends Command {
    public Insert() {
        super(new ArrayList<>(List.of("insert")), 0);
    }

    @Override
    public Response execute(Instances instances) {
        return new Response().logResult(instances.dao.create(properties));
    }
}
