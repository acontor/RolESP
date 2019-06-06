package org.rolesp.admin.commands;

import org.rolesp.BuildVars;
import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class CrearReglas extends BotCommand {

    public static final String LOGTAG = "CREATERULESCOMMAND";
    public static int crearReglas = 0;

    public CrearReglas() {
        super("crear_reglas", "Crear reglas de tu partida.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {
        SendMessage send = new SendMessage();
        send.setChatId(chat.getId().toString());
        send.setText("Escriba las reglas en el mismo mensaje.");
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(chat.getId().toString());
        delete.setMessageId(messageId);
        try {
            absSender.execute(delete);
            absSender.execute(send);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
        crearReglas = 1;
    }
}