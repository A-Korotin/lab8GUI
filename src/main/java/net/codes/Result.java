package net.codes;


import java.util.ArrayList;
import java.util.List;

public final class Result {
    public ExitCode exitCode;

    public final List<EventCode> eventCodes = new ArrayList<>();

    public String info;

    public Result exitCode(ExitCode exitCode) {
        this.exitCode = exitCode;
        return this;
    }

    public Result eventCode(EventCode code) {
        this.eventCodes.add(code);
        return this;
    }
    public Result info(String info) {
        this.info = info;
        return this;
    }

}
