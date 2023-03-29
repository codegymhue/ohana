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
public class CategoryAPI {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<CategoryResult> dtoList = categoryService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CategoryResult dto = categoryService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> category(@Valid @RequestBody CategoryCreationParam param) {
        CategoryResult dto = categoryService.create(param);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid CategoryUpdateParam param) {
        CategoryResult dto = categoryService.update(param);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
