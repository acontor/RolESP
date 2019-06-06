package org.rolesp.commands;

import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class Accion extends BotCommand {

    private static final String LOGTAG = "ACTIONCOMMAND";

    public Accion() {
        super("me", "Realizar una acción.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {

        StringBuilder messageTextBuilder = new StringBuilder();

        if (arguments != null && arguments.length > 0) {
            messageTextBuilder.append(user.getFirstName()).append(" ").append(String.join(" ", arguments));
        } else {
            messageTextBuilder.append("Escribe la acción a realizar\n");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(chat.getId().toString());
        delete.setMessageId(messageId);

        try {
            absSender.execute(delete);
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}