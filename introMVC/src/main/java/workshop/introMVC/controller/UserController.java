package workshop.introMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workshop.introMVC.exception.IncorrectDataException;
import workshop.introMVC.model.User;
import workshop.introMVC.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired 
    private UserService userService;

    @PutMapping("/updateEmail")
    public ResponseEntity<String> updateEmail(@RequestParam String oldEmail, @RequestParam String newEmail) {
        User user = userService.updateEmail(oldEmail, newEmail);
        if (user == null) {
            throw new IncorrectDataException("User with email " + oldEmail + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>("Email updated", HttpStatus.OK);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestParam String password) {
        User user = userService.updatePassword(email, password);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>("Password updated", HttpStatus.OK);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestParam String email, @RequestParam String password) {
        User user = userService.addUser(email, password);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " already exists", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<String>("User added", HttpStatus.OK);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        if (userService.getUser(email) == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(email);
        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }

}
