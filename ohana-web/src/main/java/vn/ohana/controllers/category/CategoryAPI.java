package vn.ohana.controllers.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ohana.category.CategoryService;
import vn.ohana.category.dto.CategoryCreationParam;
import vn.ohana.category.dto.CategoryUpdateParam;
import vn.ohana.entities.StatusCategory;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "status", defaultValue = "") String status) {
        if (status.trim().length() == 0) {
            return new ResponseEntity<>(categoryService.findAllPaging(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))), HttpStatus.OK);
        } else
            return new ResponseEntity<>(categoryService.findAllByStatusPaging(StatusCategory.parseStatusCategory(status), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }


    @PostMapping("/add-category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addNewCategory(@Valid @RequestBody CategoryCreationParam param) {

        return new ResponseEntity<>(categoryService.create(param), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCategoryTitle(@PathVariable Long id, @RequestBody CategoryUpdateParam param) {

        return new ResponseEntity<>(categoryService.update(param), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}/status={status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateStatusCategory(@PathVariable Long categoryId,@PathVariable String status) {
        return new ResponseEntity<>(categoryService.updateStatusCategory(categoryId, status), HttpStatus.OK);
    }
}
