package ua.tools.questions.service.stages;

import org.telegram.telegrambots.meta.api.objects.User;
import ua.tools.questions.model.Player;

import java.util.List;

public interface Stage {
    void execute(List<? extends Player> u);
}
