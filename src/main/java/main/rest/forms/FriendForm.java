package main.rest.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendForm {

    private String friendName;
    private String type;//decide que queremos hacer si añador o eliminar
}
