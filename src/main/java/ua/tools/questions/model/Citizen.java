package ua.tools.questions.model;

import org.telegram.telegrambots.meta.api.objects.User;

public class Citizen extends Player {
    @Override
    public String role() {
        return "Citizen";
    }

    public Citizen(User u) {
        super();
        setUser(u);
    }
}
