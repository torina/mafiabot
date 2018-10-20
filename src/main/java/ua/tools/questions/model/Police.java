package ua.tools.questions.model;

import org.telegram.telegrambots.meta.api.objects.User;

public class Police extends Citizen {
    @Override
    public String role() {
        return "Policeman";
    }


    public Police(User u) {
        super(u);
        setUser(u);
    }
}
