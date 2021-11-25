package recipes.security;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recipes.User;
import recipes.data.UserRepository;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    static Gson gson = new Gson();

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public ResponseEntity<String> processRegistration(@RequestBody String body) {
        try {
            System.out.println("in try block");

            RegistrationForm form = gson.fromJson(body, RegistrationForm.class);

            System.out.println("form created");

            if (userRepo.findByUsername(form.getEmail()).isPresent()) {
                System.out.println("user already exists");
               // return "<h2>User already exists</h2>";
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User user = form.toUser(passwordEncoder);
            if (!user.getUsername().contains("@") || !user.getUsername().contains(".")
                    || form.getPassword().length() < 8 || form.getPassword().trim().length() == 0) {
                System.out.println("Malformed username");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            userRepo.save(user);
            System.out.println("saved user");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("in catch block");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
