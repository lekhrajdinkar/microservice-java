package microservice.courseApp;

import microservice.jewelleryApp.repository.StudentRepository;
import microservice.jewelleryApp.repository.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class StudentServiceImpl
{
    @Autowired
    StudentRepository repo;
    TransactionTemplate transactionTemplate;


    public Student getStudent(){
        this.transactionTemplate.setReadOnly(true); // Optimized Performance
        TransactionCallback<Student> cb = (s) -> repo.getStudent("Lekhraj-Dinkar");
        return this.transactionTemplate.execute(cb);
    }

}
