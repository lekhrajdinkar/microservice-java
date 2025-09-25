package microservice.courseApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import microservice.courseApp.repository.entity.Category;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseApp/Category")
@Tag( name = "Category", description = "Category API :: CRUD Operations")
public interface CategoryApi
{
    @GetMapping("/find-all")
    List<Category> findAll();

    @GetMapping("/findById/{id}")
    Category findById(Long id);

    @PostMapping(
            value="/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Long save(@RequestBody Category category);
}
