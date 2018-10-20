package ua.tools.questions.model;

import org.telegram.telegrambots.meta.api.objects.User;

public class Doctor extends Citizen {
    @Override
    public String role() {
        return "Doctor";
    }

    public Doctor(User u) {
        super(u);
        setUser(u);
    }
}
