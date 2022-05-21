package locales;

import net.codes.EventCode;
import net.codes.ExitCode;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;

// FIXME: 20.05.2022
public class Locale {
    private String name;
    private final ResourceBundle bundle;

    public Locale(String name) throws NoSuchElementException {
        this.name = name;
        bundle = ResourceBundle.getBundle("/bundles/%s".formatted(name));
    }

    public String getName() {
        return name;
    }
    public String getMsg(EventCode code) {
        return bundle.getString(code.toString());
    }
    public String getMsg(ExitCode code) {
        return bundle.getString(code.toString());
    }
    public String getMsg(String arg) {
        return bundle.getString(arg);
    }
}
