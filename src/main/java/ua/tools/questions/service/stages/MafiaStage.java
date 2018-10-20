package ua.tools.questions.service.stages;

import org.telegram.telegrambots.meta.api.objects.User;
import ua.tools.questions.model.Citizen;

import java.util.List;
import java.util.Optional;

public class MafiaStage {

    public void execute(List<? extends Citizen> citizens, User user) {
        //doNightStage
        Optional<? extends Citizen> cit = citizens.stream().filter(c -> ((Citizen) c).getId().equals(user.getId())).findFirst();
        cit.ifPresent(citizens::remove);
    }
}
