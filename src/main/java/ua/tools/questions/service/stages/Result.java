package ua.tools.questions.service.stages;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.tools.questions.model.Citizen;
import ua.tools.questions.model.Mafia;
import ua.tools.questions.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Result {
    private List<? extends Player> players;

    private boolean cont;

    private User killedUser;
    private User healedUser;

    public Result(List<? extends Player> players) {
        this.players = players;
    }

    public Optional<User> getKilled() {
        if (killedUser.equals(healedUser)) {
            return Optional.empty();
        } else {
            players.remove(killedUser);
            return Optional.of(killedUser);
        }
    }

    public Result kill(User u) {
        killedUser = u;
        return this;
    }

    public Result heal(User u) {
        healedUser = u;
        return this;
    }


    private int getMafiaAmount() {
        int res = 0;
        for (Player p : players) {
            if (p instanceof Mafia) {
                res++;
            }
        }
        return res;
    }

    private int getCitizenAmount() {
        return players.size() - getMafiaAmount();
    }

    public boolean gameOver() {
        return getCitizenAmount() < getMafiaAmount() || getMafiaAmount() == 0;
    }

    public String getWinner() {
        if (getMafiaAmount() == 0) {
            return "Citizens have won!";
        } else {
            return "Mafia has won";
        }
    }

    public List<Citizen> getCitizens (){
        List<Citizen> res = new ArrayList<>();
        for (Player p : players) {
            if (p instanceof Citizen) {
                res.add((Citizen) p);
            }
        }
        return res;
    }

}