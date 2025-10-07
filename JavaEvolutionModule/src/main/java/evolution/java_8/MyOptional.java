package evolution.java_8;

import evolution.java_8.streamProcessing.CourseDTO;
import evolution.java_8.interfaceMore.MyInterface;
import evolution.java_8.interfaceMore.MyInterfaceImpl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyOptional {
    static void p(Object o){System.out.println(o.toString());}

    static List<String> getCourseList(){
        String[] courseArray = new String[] {"course1", "course2", "course3", "course4", "course5"}; //new String[0];
        List<String> courseList = Arrays.asList(courseArray);
        return courseList;
    }

    static CourseDTO getCourse(){
        return CourseDTO.builder()
                .title("Java 8")
                .desc("Lamba Expression, Functional Interface, Optional util Class, Method ref all 4 types")
                .build();
    }
    static void p(Object... objArr){
        Arrays.stream(objArr).forEach(System.out::println);
        System.out.println("--------------------------");
    }

    //-----------------------------------------------------------------

    public  static void main(String args[]) throws Exception
    {
        MyInterface.print(); //java 8 interface with static Function
        new MyInterfaceImpl().SayHello(); // Java 8 interface default method

        Optional<List<String>> courseListO =!getCourseList().isEmpty()
                ? Optional.of(getCourseList())
                : Optional.of(new ArrayList<String>());
        courseListO.stream().forEach(MyOptional::p);
        p(courseListO.get());
        p(courseListO.map(c->c+"_map").get());


        Optional<CourseDTO> courseO = Optional.ofNullable(getCourse()); // if course is null then returns Optional.empty()
        //courseO = Optional.of(null); // then use OfOptional
        p(courseO.isEmpty());
        p(courseO.orElseGet(()-> CourseDTO.builder().build()));
        p(courseO.orElse(CourseDTO.builder().build()));
        p(courseO.map(c->c.getDesc()).map(d->d+"---suffix").get());

        //99 ============= optional ====================
        List<String> names = Stream.of("lek", "manisha", "bryan", "dinkar", "Prasad").collect(Collectors.toList());
        Optional<List<String>> optional = Optional.of(names);
        //p("After Map : ",optional.map(x->x+" !!").get()); p("original :", optional.get());
        //p("After Filter", optional.filter(x->x.contains("bryan")).get());
        p("99. Optional MAP Operator", optional.map((List x)->{return x.size();}).get());
    }


}

class CategoryMissingException extends Exception{
    CategoryMissingException(){super("Category Unknown");}
}
