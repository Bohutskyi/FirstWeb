package test;

import frame.MessageResource;

import java.util.Locale;
import java.util.ResourceBundle;

public class testLocalization {

    public static void main(String[] args) {

        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale("ua", "UA");
        messages = ResourceBundle.getBundle("Localization/Project", currentLocale);
        System.out.println(messages.getString("greetings"));
        System.out.println(messages.getString("inquiry"));
        System.out.println(messages.getString("farewell"));

        System.out.println(MessageResource.getString("greetings"));

//        Locale currentLocale = new Locale("ua", "UA");
//        ResourceBundle messages = ResourceBundle.getBundle("loc/MessagesBundle", currentLocale);
//        System.out.println(messages.getString("greetings"));
//        System.out.println(messages.getString("inquiry"));
//        System.out.println(messages.getString("farewell"));
//
//        Localization.writeLocalization("en", "US");

        //Locale aLocale = new Locale.Builder().setLanguage("ua").setRegion("UA").build();
    }

}
