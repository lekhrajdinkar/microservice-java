package basicWebApp.courseApp.services;

import basicWebApp.courseApp.custom.Student2CustomRepository;
import basicWebApp.courseApp.repository.entity.Student2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class Student2ServiceImpl
{
    @Autowired Student2CustomRepository repo;
    @Autowired TransactionTemplate transactionTemplate;


    public Student2 getStudent(){
        this.transactionTemplate.setReadOnly(true); // Optimized Performance
        TransactionCallback<Student2> cb = (s) -> repo.getStudent("Lekhraj-Dinkar");
        return this.transactionTemplate.execute(cb);
    }

}
