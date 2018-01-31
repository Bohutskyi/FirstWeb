package frame;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageResource {

    private static ResourceBundle messageResource;

    static void loadMessages(String[] buffer) {
        messageResource = ResourceBundle.getBundle("Localization/Project", new Locale(buffer[0], buffer[1]));
    }

    public static String getString(String key) {
        return messageResource.getString(key);
    }
}
