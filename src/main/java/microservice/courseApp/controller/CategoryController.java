package microservice.courseApp.controller;

import lombok.extern.slf4j.Slf4j;
import microservice.courseApp.repository.entity.Category;
import microservice.courseApp.services.CategorySrv;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CategoryController implements CategoryApi
{
    @Autowired CategorySrv srv;

    @Override
    public List<Category> findAll() {
        log.debug("CategoryController :: findAll");
        return srv.findAll();
    }

    @Override
    public Category findById(Long id) {
        log.debug("CategoryController :: findById");
        return srv.findById(id);
    }

    @Override
    public Long save(Category category) {
        log.debug("CategoryController :: save");
        return srv.save(category);
    }
}
