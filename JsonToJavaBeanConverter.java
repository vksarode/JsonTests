import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonToJavaBeanConverter {
    public static void main(String[] args) {
        String jsonString = "{\"name\": \"John Doe\", \"age\": 30, \"emails\": [\"john.doe@example.com\", \"jdoe@example.com\"], \"address\": {\"street\": \"123 Main St\", \"city\": \"New York\"}}";
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            
            // Convert JSON string to Java bean
            Person person = objectMapper.readValue(jsonString, Person.class);
            
            // Print the converted Java bean
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Person {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("age")
    private int age;
    
    @JsonProperty("emails")
    private List<String> emails;
    
    @JsonProperty("address")
    private Address address;
    
    // Getters and setters
    
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emails=" + emails +
                ", address=" + address +
                '}';
    }
}

class Address {
    @JsonProperty("street")
    private String street;
    
    @JsonProperty("city")
    private String city;
    
    // Getters and setters
    
    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
