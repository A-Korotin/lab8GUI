package locale;

import net.codes.EventCode;
import net.codes.ExitCode;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

public class Locale {
    private final String name;
    private final ResourceBundle bundle;
    private final java.util.Locale locale;

    private static final Map<String, java.util.Locale> locales = new HashMap<>();

    static {
        locales.put("Русский", java.util.Locale.forLanguageTag("ru"));
        locales.put("Slovenščina", java.util.Locale.forLanguageTag("sl"));
        locales.put("Svenska", java.util.Locale.forLanguageTag("sv"));
        locales.put("Español (Ecuador)", java.util.Locale.forLanguageTag("es"));
        locales.put("default", java.util.Locale.forLanguageTag("en"));
    }

    public Locale(String name) throws NoSuchElementException {
        this.name = name;
        this.bundle = ResourceBundle.getBundle("/bundles/%s".formatted(name));
        this.locale = locales.get(name);
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
    public String formatDate(LocalDate date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        Date convertedDate = java.sql.Date.valueOf(date);
        return dateFormat.format(convertedDate);
    }
}
