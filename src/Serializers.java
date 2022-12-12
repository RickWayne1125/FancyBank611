import Person.Customer.Customer;

import java.util.HashMap;
import java.util.Map;

public class Serializers {
    public static Map<String, String> CustomerSerializer(Customer customer){
        Map<String, String> serializedCustomer = new HashMap<>();
        serializedCustomer.put("username", customer.getUsername());
        serializedCustomer.put("firstName", customer.getFirstName());
        serializedCustomer.put("middleName", customer.getMiddleName());
        serializedCustomer.put("lastName", customer.getLastName());
        serializedCustomer.put("email", customer.getEmail());
        serializedCustomer.put("password", customer.getPassword());
        serializedCustomer.put("contact", customer.getContact());
        serializedCustomer.put("address", customer.getAddress());
        return serializedCustomer;
    }
}
