package rmq;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Student2 implements Serializable {
    private String id;
    private String name;
    private int age;
}

