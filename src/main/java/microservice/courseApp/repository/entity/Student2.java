package microservice.courseApp.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import microservice.courseApp.custom.enums.GenderEnum;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@Table(name="STUDENT2")
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@NamedQueries({
        @NamedQuery(name = "Student2.findStudentByName", query = "select s from Student2 s where s.name=:sname"),
        @NamedQuery(name = "Student2.findStudentTuple", query = "select s.name, s.birthDate as dob, s.gender as gender from Student2 s")
})
public class Student2 {
    @Id
    @GeneratedValue(generator = "myUUID")
    //@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @GenericGenerator(name = "myUUID"
            ,strategy = "microservice.courseApp.custom.CustomIdentifier"
            //,parameters = @Parameter(name = "prefix", value = "prod")
    )
    private UUID uuid;

    @Column(name="student_name", length=50, nullable=false, unique=false)
    private String name;

    @Transient
    private Integer age;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
}
