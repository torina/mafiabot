package ua.tools.questions.service.stages;

import org.telegram.telegrambots.meta.api.objects.User;

public class DoctorStage  {
    //todo edit
    public Result execute(Result res, User user) {
        return res.heal(user);
    }
}
