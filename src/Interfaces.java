import java.util.Map;

public class Interfaces {
    /* Customer Functions */
    // Customer Login
    public static Map<String, String> loginCustomer(Map<String, String> data) {
        return Serializers.CustomerSerializer(Controller.loginCustomer(data.get("username"), data.get("password")));
    }
}
