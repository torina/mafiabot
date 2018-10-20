package ua.tools.questions.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.tools.questions.service.stages.DoctorStage;
import ua.tools.questions.service.stages.MafiaStage;
import ua.tools.questions.service.stages.PoliceStage;
import ua.tools.questions.service.stages.Result;

@Component
@Data
public class GameController {

    private String gameId;

    private Result result;

    @Autowired
    MafiaStage mafiaStage;

    @Autowired
    PoliceStage policeStage;

    @Autowired
    DoctorStage doctorStage;

    @Setter
    @Getter
    private Long mainChatId;

    Result r;
    //executeStagesHere


//    private void callMafia(User u) {
//        r = mafiaStage.execute(u);
//    }

    private void removePerson(User u) {
        r.getPlayers().remove(u);
    }


}
