package microservice.courseApp.repository;

import jakarta.persistence.*;
import microservice.courseApp.custom.enums.GenderEnum;
import microservice.courseApp.repository.entity.Student2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class Student2Repository
{
    @Autowired  EntityManagerFactory sessionFactory;

    public Student2 getStudent(String name){
        EntityManager em = sessionFactory.createEntityManager();

        TypedQuery<Student2> q = em.createQuery("select s from Student s where s.name=:sname", Student2.class);
        q.setParameter("sname",name);

        TypedQuery<Student2> nq = em.createNamedQuery("Student.findStudentByName", Student2.class);
        nq.setParameter("sname",name);

        Student2 result =  nq.getSingleResult(); //getResultList()/Set()
        em.close();
        return result;
    }

    public Student2 addStudent(String name){
        Student2 s = Student2.builder()
                .age(32)
                .uuid(UUID.randomUUID())
                .gender(GenderEnum.FEMALE)
                .birthDate(LocalDate.of(1991, 5, 18))
                .name(name)
                .build();
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(s);
        transaction.commit();
        return s;
    }

    public List<Tuple> getTuple(){
        EntityManager em = sessionFactory.createEntityManager();
        // Tuple === Object[]
        TypedQuery<Tuple> nq = em.createNamedQuery("Student.findStudentTuple", Tuple.class);
        List<Tuple> result =  nq.getResultList();
        em.close();
        return result;
    }
}
