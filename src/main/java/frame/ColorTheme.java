package frame;

import java.awt.*;

public class ColorTheme {

    private static Color color1, color2;
    private static Font font;

    static void loadColor1(String[] buffer) {
        color1 = new Color(Integer.parseInt(buffer[0]), Integer.parseInt(buffer[1]), Integer.parseInt(buffer[2]));
    }

    static void loadColor2(String[] buffer) {
        color2 = new Color(Integer.parseInt(buffer[0]), Integer.parseInt(buffer[1]), Integer.parseInt(buffer[2]));
    }

    public static Color getColor1() {
        return color1;
    }

    public static Color getColor2() {
        return color2;
    }
}
