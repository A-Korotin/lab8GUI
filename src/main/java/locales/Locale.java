package locales;

import net.codes.EventCode;
import net.codes.ExitCode;

public interface Locale {
    String getMessage(EventCode eventCode);
    String getMessage(ExitCode exitCode);
}
