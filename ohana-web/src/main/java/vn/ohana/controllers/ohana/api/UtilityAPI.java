package vn.ohana.controllers.ohana.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ohana.utility.dto.UtilityResult;
import vn.ohana.utility.UtilityService;

import java.util.List;

@RestController
@RequestMapping("/api/utilities")
public class UtilityAPI {

    @Autowired
    UtilityService utilityService;

    @GetMapping()
    public ResponseEntity<?> getUtilities() {
        List<UtilityResult> utilities = utilityService.findAll();
        return new ResponseEntity<>(utilities, HttpStatus.OK);
    }
}