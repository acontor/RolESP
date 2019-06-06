package org.rolesp.admin.commands;

import org.rolesp.game.Usuario;
import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class RegistrarAdministrador extends BotCommand {

    public static final String LOGTAG = "REGISTRARADMINISTRADOR";

    public RegistrarAdministrador() {
        super("registrar", "Regístrate en el juego.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {
        Usuario usuario = new Usuario(user.getFirstName(), user.getUserName(), "Administrador", user.getId());
        SendMessage send = new SendMessage();
        if (usuario.insertar(usuario)) {
            send.setChatId(chat.getId().toString());
            send.setText("Usuario creado.");
        } else {
            send.setChatId(chat.getId().toString());
            send.setText("El usuario ya está creado.");
        }
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(chat.getId().toString());
        delete.setMessageId(messageId);
        try {
            absSender.execute(delete);
            absSender.execute(send);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}