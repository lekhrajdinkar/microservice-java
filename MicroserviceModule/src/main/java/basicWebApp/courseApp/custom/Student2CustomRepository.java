package basicWebApp.courseApp.custom;

import jakarta.persistence.*;
import basicWebApp.courseApp.custom.enums.GenderEnum;
import basicWebApp.courseApp.repository.entity.Student2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class Student2CustomRepository
{
    @Autowired  EntityManagerFactory sessionFactory; // 👈🏻
    private final String namesQuery_1 = "Student2.findStudentByName"; // check entity : Student2.java
    private final String namesQuery_2 = "Student2.findStudentTuple";

    //=============
    // C R u D
    //=============

    // ▶️ Create
    public Student2 addStudent(String name)
    {
        Student2 s = Student2.builder()
                .age(32)
                .uuid(UUID.randomUUID())
                .gender(GenderEnum.FEMALE)
                .birthDate(LocalDate.of(1991, 5, 18))
                .name(name)
                .build();

        EntityManager em = sessionFactory.createEntityManager(); // 👈🏻
        EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(s);
            transaction.commit();

        return s;
    }

    // ▶️ Read (Student)
    public Student2 getStudent(String name)
    {
        EntityManager em = sessionFactory.createEntityManager();

        TypedQuery<Student2> q = em.createQuery("select s from Student2 s where s.name=:sname", Student2.class);
        q.setParameter("sname",name);

        TypedQuery<Student2> nq = em.createNamedQuery(namesQuery_1, Student2.class);
        nq.setParameter("sname",name);

        Student2 result =  nq.getSingleResult(); //getResultList()/Set()
        em.close();
        return result;
    }

    // ▶️ Read 2 (tuple)
    // Tuple === [ Object1, Object2, ... , ObjectN ]
    public List<Tuple> getAllStudent_tuple()
    {
        EntityManager em = sessionFactory.createEntityManager();
        TypedQuery<Tuple> nq = em.createNamedQuery(namesQuery_2, Tuple.class);
        List<Tuple> result =  nq.getResultList();
        em.close();
        return result;
    }
}
