package Utils;

public class IO {
    public static void displayMessage(String message, MessageType type){
        switch(type){
            case INFO:
                System.out.println("[INFO] " + message);
                break;
            case WARNING:
                System.out.println("[WARNING] " + message);
                break;
            case ERROR:
                System.out.println("[ERROR] " + message);
                break;
        }
    }
}
