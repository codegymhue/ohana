package vn.ohana.controllers.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.category.CategoryService;
import vn.ohana.category.dto.CategoryResult;
import vn.ohana.category.dto.CategoryCreationParam;
import vn.ohana.category.dto.CategoryUpdateParam;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryAPI {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {

        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addNewCategory(@Valid @RequestBody CategoryCreationParam param) {

        return new ResponseEntity<>(categoryService.create(param), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategoryTitle(@PathVariable Long id, @RequestBody CategoryUpdateParam param) {

        return new ResponseEntity<>(categoryService.update(param), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
