package org.rolesp.admin.commands;

import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class CrearJuego extends BotCommand {

    public static final String LOGTAG = "CREATEGAMECOMMAND";
    public static int crearJuego = 0;

    public CrearJuego() {
        super("crear_juego", "Crear nueva partida.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {
        SendMessage send = new SendMessage();
        send.setChatId(chat.getId().toString());
        send.setText("¿Como se llamará tu partida?");
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(chat.getId().toString());
        delete.setMessageId(messageId);
        try {
            absSender.execute(delete);
            absSender.execute(send);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
        crearJuego = 1;
    }
}
