package ua.tools.questions.model;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.User;

@Data
public abstract class Player {

    private Integer id;
//    private String name;

    private User user;

    abstract String role();




}
