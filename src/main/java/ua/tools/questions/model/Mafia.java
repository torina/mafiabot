package ua.tools.questions.model;

import org.telegram.telegrambots.meta.api.objects.User;

public class Mafia extends Player {

    public Mafia(User u) {
        super();
        setUser(u);
    }

    @Override
    public String role() {
        return "Mafia";
    }
}
