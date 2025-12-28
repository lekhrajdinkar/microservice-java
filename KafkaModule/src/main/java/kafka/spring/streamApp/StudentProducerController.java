package kafka.spring.streamApp;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import static kafka.spring.streamApp.KafkaConfigJsonProducer.TOPIC1;

@RestController
@RequestMapping("/api/v1/students")
public class StudentProducerController {

    private final KafkaTemplate<String, StudentJson> kafkaTemplate;

    public StudentProducerController(KafkaTemplate<String, StudentJson> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishStudent(@RequestBody StudentJson student) {
        kafkaTemplate.send(TOPIC1, student);
        return ResponseEntity.ok("published");
    }
    @PostMapping("/publish-streams/{count}")
    public ResponseEntity<String> publishStudentStreams(@RequestBody StudentJson student, @PathVariable int count) {
        for(int i=1;i<=count;i++)
        {
            StudentJson s = new StudentJson();
            s.setId(student.getId() + "-" + i);
            s.setName(student.getName() + "-" + i);
            s.setAge(student.getAge() + i);
            kafkaTemplate.send(TOPIC1, s);
        }

        return ResponseEntity.ok("published");
    }
}
