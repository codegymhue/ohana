package vn.ohana.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ohana.utility.UtilityService;
import vn.ohana.utility.dto.UpdateUtilityParam;
import vn.ohana.utility.dto.UtilityResult;

import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class UtilityRestController {

    @Autowired
    private UtilityService utilitiesService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<UtilityResult> optionalUtility = utilitiesService.findById(id);
        if (optionalUtility.isPresent()) {
            return new ResponseEntity<>(optionalUtility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error for get customer", HttpStatus.NO_CONTENT);
        }
    }

//    @GetMapping("/utility")
//    public ResponseEntity<?> getUtility() {
////        List<UtilityResult> posts = utilitiesService.findAllByStatus(StatusUtility.SHOW);
////        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }


    @PutMapping("/utility")
    public ResponseEntity<?> doUpdate(@RequestBody UpdateUtilityParam param) {
        UtilityResult dto = utilitiesService.update(param);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/hidden/{id}")
    public ResponseEntity<?> doHidden(@PathVariable Long id) {
        utilitiesService.hidden(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        utilitiesService.deleteById(id);
        return new ResponseEntity<>("Delete customer successful", HttpStatus.OK);

    }
}
