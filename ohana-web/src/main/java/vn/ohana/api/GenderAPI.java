//package vn.ohana.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vn.ohana.gender.GenderService;
//import vn.tg.ohana.repository.model.Gender;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/genders")
//public class GenderAPI {
//    @Autowired
//    GenderService genderService;
//
//    @GetMapping()
//    public ResponseEntity<?> getGenders() {
//        List<Gender> categories = genderService.findAll();
//        return new ResponseEntity<>(categories, HttpStatus.OK);
//    }
//}
