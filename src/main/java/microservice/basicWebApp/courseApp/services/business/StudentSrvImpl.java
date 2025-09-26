package microservice.basicWebApp.courseApp.services.business;

import microservice.basicWebApp.courseApp.repository.StudentDAO;
import microservice.basicWebApp.courseApp.repository.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentSrvImpl implements StudentSrv
{
    @Autowired
    StudentDAO dao;


    @Override
    public List<Student> findAll() {
        return dao.findAll();
    }

    @Override
    public Student findById(Long aLong) {
        return dao.findById(aLong).get();
    }

    @Override
    public Long save(Student student) {
        return dao.save(student).getId();
    }

    @Override
    public Student update(Student student) {
        return dao.save(student);
    }

    @Override
    public Student delete(Student student) {
         dao.delete(student);
        return student;
    }
}
