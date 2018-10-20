package ua.tools.questions.service.stages;

import org.telegram.telegrambots.meta.api.objects.User;
import ua.tools.questions.model.Mafia;
import ua.tools.questions.model.Player;

import java.util.List;
import java.util.Optional;

public class PoliceStage {

//    @Override
    public boolean check(List<Player> playerList, User user) {
        Optional<Player> pl = playerList.stream().filter(player -> player.getId().equals(user.getId())).findFirst();
        return pl.isPresent() && pl.get() instanceof Mafia;
    }
}
