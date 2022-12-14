package Utils;

import API.Controller;

public class IO {
    public static void displayMessage(String message, MessageType type) {
        switch (type) {
            case INFO:
                if (Config.DEBUG) {
                    System.out.println(TextColors.GREEN + "[INFO] " + TextColors.RESET + message);
                }
                break;
            case WARNING:
                System.out.println(TextColors.YELLOW + "[WARNING] " + TextColors.RESET + message);
                break;
            case ERROR:
                System.out.println(TextColors.RED + "[ERROR] " + TextColors.RESET + message);
                break;
        }
    }
}
