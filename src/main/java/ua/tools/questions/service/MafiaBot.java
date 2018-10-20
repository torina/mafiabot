package ua.tools.questions.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.tools.questions.external.ResponseTT;
import ua.tools.questions.external.TynTec;
import ua.tools.questions.model.Citizen;
import ua.tools.questions.model.Doctor;
import ua.tools.questions.model.Mafia;
import ua.tools.questions.model.Player;
import ua.tools.questions.service.stages.Result;

import java.util.*;

//import org.telegram.telegrambots.meta.generics.WebhookBot;

//switch to webhook
@Component
@Slf4j
public class MafiaBot extends TelegramLongPollingBot /*extends WebhookBot */ {

    public static final int NUM_PARTICIPANTS = 2;
    @Autowired
    TynTec tynTecGateway;

    @Autowired
    GameController gameController;

    private boolean gameStarted = false;

    Integer mainChat;

    List<Player> roledPlayers;

    Map<Integer, String> userIdToOtp = new HashMap<>();

    private Map<User, Long> userToChat = new HashMap<>();
    private Map<Player, Long> roleToChat = new HashMap<>();


    @Autowired
    PlayerRegister playerRegister;


    @Override
    public String getBotUsername() {
        return "vikingtebot";
    }

    @Override
    public String getBotToken() {
        return "625995806:AAGMhoaORBdjEhWfMQy72u_qoExH1pgOioM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("UPDATE: " + update);

        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            if (messageText.startsWith("/play")) {

                if (!gameStarted) {
                    gameStarted = true;
                    Long chatId = update.getMessage().getChatId();
//                String gameName = messageText.replaceAll("/PLAY", "");

                    if (playerRegister.getRegisteredUsers().size() < NUM_PARTICIPANTS) {
                        sendReply(chatId, "Sorry, too little participants. Next time :( ");
                    } else {
                        sendReply(chatId, "GAME STARTED!");
                        playGame();
                    }

                }

            } else if (messageText.startsWith("/register")) {

                User user = update.getMessage().getFrom();
                Message message = update.getMessage();

                String phone = messageText.substring(17);
                Optional<ResponseTT> responseTT = tynTecGateway.sendVerificationCode(phone);
                if (responseTT.isPresent()) {
                    sendReply(message.getChatId(), "To continue registration type: /validate + whitespace + your SMS code");
                    userIdToOtp.put(user.getId(), responseTT.get().getOtpId());
                } else {
                    sendReply(message.getChatId(), "Error. Try to register one more time.");
                }


            } else if (messageText.startsWith("/validate")) {
                String code = messageText.substring(10);
                User user = update.getMessage().getFrom();
                String otpId = userIdToOtp.get(user.getId());
                Boolean isOk = tynTecGateway.verifyCode(otpId, code);
                Message message = update.getMessage();
                if (isOk) {
                    userToChat.put(user, message.getChatId());
                    playerRegister.registerUser(user, message);
                    sendReply(message.getChatId(), "Successfully registered!");
                    gameController.setMainChatId(message.getChatId());
                    userIdToOtp.remove(user.getId());
                } else {
                    sendReply(message.getChatId(), "Sorry, smth went wrong!");
                }

            } else if (messageText.startsWith("/kill")) {
                if(roledPlayers.size() >=  NUM_PARTICIPANTS) {
                    String userName = messageText.substring(5);
                    for (Map.Entry<Player, Long> entry : roleToChat.entrySet()) {
                        sendReply(entry.getValue(), userName + " was killed (RIP)");
                    }
                    Optional<Player> playerOptional =
                            roledPlayers.stream().filter(player -> player.getUser().getFirstName().startsWith(userName)).findFirst();

                    gameController.mafiaStage.execute(gameController.getResult().getCitizens(), playerOptional.get().getUser());
                }

            } else if (messageText.startsWith("/check")) {

                //todo implement
            } else if (messageText.startsWith("/heal")) {
                //todo implement
            }
        }
    }

    private void playGame() {
        init();

        for (Map.Entry<Player, Long> entry : roleToChat.entrySet()) {
            sendReply(entry.getValue(), "The city sleeps...");
        }

        for (Map.Entry<Player, Long> entry : roleToChat.entrySet()) {
            sendReply(entry.getValue(), "Mafia wakes up. What is your victim? Type: /kill + name");
        }

//        sendReply(gameController.getMainChatId(), "Mafia wakes up. What is your victim?");
    }

    private void init() {
        List<User> usersKeys = playerRegister.getRegisteredUsers();
        Collections.shuffle(usersKeys);

        roledPlayers = new ArrayList<>();
        //send roles
        int i = 0;
        Mafia m1 = new Mafia(usersKeys.get(i));
        roledPlayers.add(m1);
        roleToChat.put(m1, userToChat.get(usersKeys.get(i)));
        sendReply(userToChat.get(usersKeys.get(i++)), "You are: " + m1.role());

//        Mafia m2 = new Mafia(usersKeys.get(i));
//        roledPlayers.add(m2);
//        sendReply(userToChat.get(usersKeys.get(i++)), "You are: " + m2.role());

//        if(usersKeys.size() <=i ) {
//            Police pol = new Police(usersKeys.get(i));
//            roledPlayers.add(pol);
//            sendReply(userToChat.get(usersKeys.get(i++)), "You are: " + pol.role());
//        }
        if (usersKeys.size() > i) {
            Doctor doc = new Doctor(usersKeys.get(i));
            roledPlayers.add(doc);
            roleToChat.put(doc, userToChat.get(usersKeys.get(i)));
            sendReply(userToChat.get(usersKeys.get(i++)), "You are: " + doc.role());
        }
        if (usersKeys.size() > i) {
            Citizen cit1 = new Citizen(usersKeys.get(i));
            roledPlayers.add(cit1);
            roleToChat.put(cit1, userToChat.get(usersKeys.get(i)));
            sendReply(userToChat.get(usersKeys.get(i++)), "You are: " + cit1.role());
        }
//        Citizen cit2 = new Citizen(usersKeys.get(i));
//        roledPlayers.add(cit1);
//        sendReply(userToChat.get(usersKeys.get(i)), "You are: " + cit2.role());

        gameController.setResult(new Result(roledPlayers));
    }

    private void sendReply(Long chatId, String msg) {
        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(msg);


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        System.out.println("UPDATES!!!");
        // We check if the update has a message and the message has text
        for (Update u : updates) {
            onUpdateReceived(u);
        }
    }
}
