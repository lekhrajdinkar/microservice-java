package evolution.java_8.streamProcessing;

import evolution.java_8.Collection.Student;
import java.util.*;
import java.util.stream.Collectors;
import static evolution.Print.p;

public class MyCollecters3
{
    public static <HasSet> void main(String a[])
    {
         List<Student> students = new ArrayList<>();
            Student s1 = Student.builder().firstname("Lekhraj").lastname("Dinkar").build();
            Student s2 = Student.builder().firstname("Lekhraj2").lastname("Dinkar2").build();
            Student s3 = Student.builder().firstname("Manisha").lastname("prasad").build();
            students.add(s1); students.add(s2); students.add(s3);
         //------------ Java Collector ----------

         // ====== 1. Collector.mapping
        //1.1  java8 way
        List<String> r1=students
                .stream()
                .map(s->s.getFirstname()+"--"+s.getLastname() )
                .collect(Collectors.toList());
        p(r1);

        //1.2 Jav 9 way
        r1=students
                .stream()
                .collect(Collectors.mapping(
                        s->s.getFirstname()+"--"+s.getLastname(),
                        Collectors.toList()
                ));
        p(r1);

        //1.3 group by - lek and other. grouping last
        Map<String,List<String>> r2=students
                .stream()
                .collect(Collectors.mapping(
                        s->s.getFirstname()+"--"+s.getLastname(),
                        Collectors.groupingBy(x->x.contains("Lekhraj")?"Lek":"others")
                ));
        p(r2);

        // 1.4 group by - lek and other. grouping first
        r2 = students
                .stream()
                .collect(Collectors.groupingBy(
                        s->s.getFirstname().equals("Lekhraj") ? "lek" : "Others",
                        Collectors.mapping(
                                s->s.getFirstname()+"--"+s.getLastname(),
                                Collectors.toList())
                ));
        p(r2);

    }
}


