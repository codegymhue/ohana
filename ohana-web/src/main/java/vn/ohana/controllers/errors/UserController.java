package vn.ohana.controllers.errors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.ohana.user.dto.SignUpParam;

import java.util.ArrayList;

@Controller
public class UserController {



    @GetMapping("/add")
    public String showAddUserForm(@ModelAttribute("user") SignUpParam user) {
        return "/errors/addUser";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") SignUpParam user, BindingResult result, Model model) {

        String err = ";";//validationService.validateUser(user);

        if (!err.isEmpty()) {
            ObjectError error = new ObjectError("globalError", err);
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "/errors/addUser";
        }

        model.addAttribute("users", new ArrayList<User>());
        return "/errors/home";
    }

}
