package recipes.security;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import recipes.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
    public class RegistrationForm {

        @Email
        private String email;

        @NotBlank
        @Length(min = 8)
        private String password;


        public User toUser(PasswordEncoder passwordEncoder) {
            return new User(this.email, passwordEncoder.encode(this.password));
        }

    }
