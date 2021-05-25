package main.rest.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserForm {

    private String password;
    private String username;
    private String email;

    public UserForm() {
    }


}
