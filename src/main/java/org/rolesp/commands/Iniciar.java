package org.rolesp.commands;

import org.rolesp.game.Preferencias;
import org.rolesp.game.Reglas;
import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatTitle;
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
 * @author Timo Schulz (Mit0x2)
 */
public class Iniciar extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";
    public static Preferencias preferences = new Preferencias();
    public static Reglas reglas = new Reglas();

    public Iniciar() {
        super("iniciar", "Iniciar el juego y cargar datos.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        if(preferences.cargar()) {
            /* Métodos para cargar lo demás */
            reglas.cargar();
            /* Setear Titulo del chat e imagen, en la descripción incluir participantes */
            SetChatTitle set = new SetChatTitle();
            set.setChatId(chat.getId().toString());
            set.setTitle(Preferencias.nombre);
            try {
                absSender.execute(set);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        } else {
            messageBuilder.append("No existe ninguna partida.\n").append("El administrador tendrá que crearla para empezar.");
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText(messageBuilder.toString());
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
}