package vn.ohana.controllers.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.ohana.utility.UtilityService;
import vn.ohana.utility.dto.CreateUtilityParam;
import vn.ohana.utility.dto.UpdateUtilityParam;

@RestController
@RequestMapping("api/utilities")
public class UtilityAPI {
    @Autowired
    UtilityService utilityService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "status") String status ) {
        if (status.trim().length() == 0) {
            return new ResponseEntity<>( utilityService.findAll(PageRequest.of(page, size)), HttpStatus.OK);
        }
        else return new ResponseEntity<>( utilityService.findAllByStatus(PageRequest.of(page, size),status), HttpStatus.OK);
    }

    @GetMapping("/{utilityId}")
    public ResponseEntity<?> findAll(@PathVariable int utilityId) {
        return new ResponseEntity<>(utilityService.getById(utilityId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> createNew(@RequestBody CreateUtilityParam params) {
        return new ResponseEntity<>(utilityService.save(params), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{utilityId}")
    public ResponseEntity<?> update(@PathVariable int utilityId,@RequestBody UpdateUtilityParam params) {
        return new ResponseEntity<>(utilityService.update(params), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{utilityId}/status={status}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer utilityId,@PathVariable String status) {
        return new ResponseEntity<>(utilityService.updateStatus(utilityId,status), HttpStatus.OK);
    }

}
