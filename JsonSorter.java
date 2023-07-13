import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class JsonSorter {
    public static void main(String[] args) throws Exception {
        String json = "{\n" +
                "  \"person\": {\n" +
                "    \"name\": \"John\",\n" +
                "    \"age\": 30,\n" +
                "    \"addresses\": [\n" +
                "      {\n" +
                "        \"type\": \"home\",\n" +
                "        \"city\": \"New York\",\n" +
                "        \"zip\": 10001,\n" +
                "        \"code\": \"A\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"work\",\n" +
                "        \"city\": \"San Francisco\",\n" +
                "        \"zip\": 94101,\n" +
                "        \"code\": \"C\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"office\",\n" +
                "        \"city\": \"Chicago\",\n" +
                "        \"zip\": 60601,\n" +
                "        \"code\": \"B\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        // Convert JSON string to JsonNode object
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // Sort JSON object by keys
        SortedMap<String, JsonNode> sortedMap = new TreeMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        // Sort JSON object by values
        List<Map.Entry<String, JsonNode>> sortedList = new ArrayList<>(sortedMap.entrySet());
        Collections.sort(sortedList, (o1, o2) -> {
            JsonNode node1 = o1.getValue();
            JsonNode node2 = o2.getValue();

            if (node1.isNumber() && node2.isNumber()) {
                return Double.compare(node1.doubleValue(), node2.doubleValue());
            } else if (node1.has("code") && node2.has("code")) {
                return node1.get("code").asText().compareTo(node2.get("code").asText());
            } else {
                return node1.toString().compareTo(node2.toString());
            }
        });

        // Create a new sorted JSON object
        ObjectNode sortedObject = objectMapper.createObjectNode();
        for (Map.Entry<String, JsonNode> entry : sortedList) {
            sortedObject.set(entry.getKey(), entry.getValue());
        }

        // Convert sorted JSON object back to string
        String sortedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sortedObject);
        System.out.println(sortedJson);
    }
}
