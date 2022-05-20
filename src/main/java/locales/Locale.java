package locales;

import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.NoSuchElementException;

// FIXME: 20.05.2022
public class Locale {
    private String name;

    public Locale(String name) throws NoSuchElementException {
        this.name = name;
    }

    public String getMsg(EventCode code) {
        return null;
    }
    public String getMsg(ExitCode code) {
        return null;
    }
    public String getMsg(String arg) {
        return null;
    }
}
