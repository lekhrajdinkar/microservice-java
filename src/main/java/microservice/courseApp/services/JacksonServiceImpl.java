package microservice.courseApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import evolution.java_7.enums.CatLevel;
import lombok.extern.slf4j.Slf4j;
import microservice.courseApp.repository.dto.CategoryDTO;
import microservice.courseApp.repository.dto.CourseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class JacksonServiceImpl
{
    public void deserializeDemo() throws JsonProcessingException
    {
        //Unmarshall an Incomplete JSON
        String json =  "{\"id\":1,\"title2\":\"category-test\",\"localDate\":\"2020\", \"level\":\"PRO\"}"; //Unrecognized field "title2"

        CategoryDTO dto = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // if we pass title3, it will fail
                .configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
                .readerFor(CategoryDTO.class)
                .readValue(json);

       log.info("CategoryDTO - {}", dto);

        // JsonNode
        //a JSON can be parsed into a JsonNode object and used to retrieve data from a specific node:
        JsonNode node = new ObjectMapper().readTree(json);
        node.get("title2");
       log.info("JsonNode - {}",node);
       log.info("JsonNode.title2 - {}",node.get("title2"));

        // Convert into Map
        Map map = new ObjectMapper().readValue(json, new TypeReference<Map>() {});
       log.info("Json read - {}",map);

        // Convert into List
        // https://www.baeldung.com/jackson-object-mapper-tutorial
    }

    public void serializeDemo() throws JsonProcessingException
    {
        //make DTO
        CourseDTO c1 = CourseDTO.builder().id(100L).title("c1").desc("c1-desc").instructorId(100L).categoryId(100L).build();
        CourseDTO c2 = CourseDTO.builder().id(200L).title("c2").desc("c2-desc").instructorId(100L).categoryId(100L).build();
        CourseDTO c3 = CourseDTO.builder().id(300L).title("c3").desc("c3-desc").instructorId(100L).categoryId(100L).build();

        List courses = List.of(c1,c2,c3);

        Map courseMap = Map.of("NOVICE", courses,"SEMI-PRO", courses, "PRO", courses);

        CategoryDTO dto = CategoryDTO.builder()
                .id(100L)
                .level(CatLevel.NOVICE)
                .desc("REST API deserialization")
                .title("REST")
                .localDate(LocalDate.now())
                .optionalString(Optional.of("optional-Value"))
                .testMap(Map.of("k1","v1","k2","v2","k3","v3"))
                //.courses(courses)
                //.coursesMap(courseMap)
                .build();

        //System.out.println(dto);

        //Serialize
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dto);

       log.info(json);
    }

    // 1. Deserialize JSON with injected values
    void deserialize(){
        ObjectMapper mapper = new ObjectMapper();

        // Example JSON
        String json = "{\n" +
                "    \"jewelleryNameAlias2\": \"Jewellery-3\",\n" +
                "    \"jewelleryId\": 34,\n" +
                "    \"jewelleryPrice\": 300,\n" +
                "    \"derive\": \"XXXXXX\",\n" +
                "    \"category\": {\n" +
                "        \"type\": \"BRACLET\"\n" +
                "    }\n" +
                "}";
    }
}
