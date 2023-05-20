import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonToMapExample {

    public static void main(String[] args) {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"address\":[{\"city\":\"New York\",\"state\":\"NY\"},{\"city\":\"London\",\"state\":\"UK\"}]}";
        String path = "address.city";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);
            List<Object> values = getValuesByPath(jsonMap, path);

            if (!values.isEmpty()) {
                System.out.println("Values at path '" + path + "': " + values);
            } else {
                System.out.println("Path '" + path + "' does not exist or has no values.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Object> getValuesByPath(Map<String, Object> jsonMap, String path) {
        String[] keys = path.split("\\.");

        List<Object> result = new ArrayList<>();

        extractValues(jsonMap, keys, 0, result);

        return result;
    }

    private static void extractValues(Object obj, String[] keys, int index, List<Object> result) {
        if (index >= keys.length) {
            result.add(obj);
            return;
        }

        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object item : list) {
                extractValues(item, keys, index, result);
            }
        } else if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            Object value = map.get(keys[index]);
            if (value != null) {
                extractValues(value, keys, index + 1, result);
            }
        }
    }
}
