package microservice.courseApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import microservice.courseApp.repository.entity.Category;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CRUD")
@Tag( name = "Category", description = "Category API :: CRUD Operations")
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
