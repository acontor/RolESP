package org.rolesp.commands;

import org.rolesp.game.Preferencias;
import org.rolesp.services.BotCommand;
import org.rolesp.services.Emoji;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;


/**
 * This commands starts the conversation with the bot
 *
 */
public class Reglas extends BotCommand {

    public static final String LOGTAG = "RULESCOMMAND";

    public Reglas() {
        super("reglas", "Obtener reglas de tu partida.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        SendMessage answer = new SendMessage();
        if (org.rolesp.game.Reglas.reglas != null) {
            messageBuilder.append(Emoji.NO_ENTRY_SIGN).append(" Reglas para ").append(Preferencias.nombre).append(" ").append(Emoji.NO_ENTRY_SIGN);
            messageBuilder.append("\n\n").append(org.rolesp.game.Reglas.reglas);
            answer.setChatId(user.getId().toString());
            answer.setText(messageBuilder.toString());
        } else {
            answer.setChatId(user.getId().toString());
            answer.setText("No existen reglas aún.");
        }
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