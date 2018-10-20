package ua.tools.questions.service;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

@Component
public class PlayerRegister {

    @Getter
    private Map<String, List<User>> codeToUser;


    @Getter
    private List<User> registeredUsers = new ArrayList<>();

//    public void registerUserWithGameCode(User user, Message message) {
//        if (codeToUser == null) {
//            codeToUser = new HashMap<>();
//        }
//        if (codeToUser.get(message.getText().substring(10, 17)) == null) {
//            List<User> urs = new LinkedList<>();
//            urs.add(user);
//            codeToUser.put(message.getText().substring(10, 17), urs);
//
//        } else {
//            codeToUser.get(message.getText().substring(10, 17)).add(user);
//        }
//    }

    public void registerUser(User user, Message message) {

        registeredUsers.add(user);

    }

//    public void registerGame(Update update) {
//        throw new NotImplementedException();
//    }
}
