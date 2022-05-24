package exceptions;

import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.List;

public class NonOKExitException extends RuntimeException {

    private final ExitCode exitCode;
    private final List<EventCode> eventCodes;

    public NonOKExitException(ExitCode exitCode, List<EventCode> eventCodes) {
        this.exitCode = exitCode;
        this.eventCodes = eventCodes;
    }

    public ExitCode getExitCode() {
        return exitCode;
    }

    public List<EventCode> getEventCodes() {
        return eventCodes;
    }
}
