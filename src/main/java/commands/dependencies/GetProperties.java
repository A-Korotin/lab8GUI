package commands.dependencies;

import exceptions.InvalidValueException;
import io.Properties;

import java.util.List;

/**
 * Вспомогательный класс для получения Properties от пользователя из разных источников
 */
public final class GetProperties {
    public static Properties getProperties(boolean askForInput, List<String> args, Instances instances, int indexShift) throws InvalidValueException {
        Properties properties;
        if (askForInput) {
            properties = instances.consoleRequester.requestProperties();
        }
        else {
            properties = Properties.parseProperties(args, indexShift);
        }
        return properties;
    }

}
