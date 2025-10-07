package basicWebApp.courseApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import basicWebApp.courseApp.repository.entity.Category;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CRUD")
@Tag( name = "CRUD Operations", description = "CRUD Operations - category(3), course(3), instructor(3),  student(3), student2(1). UD are pending")
public interface CrudApi
{
    @GetMapping("/Category/find-all")
    List<Category> findAll_Category();

    @GetMapping("/Category/find-by-id/{id}")
    Category findById_Category(Long id);

    @PostMapping(
            value="/Category/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Long save_Category(@RequestBody Category category);
}
