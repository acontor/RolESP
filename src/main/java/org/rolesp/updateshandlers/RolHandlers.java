package org.rolesp.updateshandlers;

import org.rolesp.BotConfig;
import org.rolesp.BuildVars;
import org.rolesp.commands.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class RolHandlers extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";

    /**
     * Constructor.
     */
    public RolHandlers(String botUsername) {
        super(botUsername);
        /* Nunca pasa por aqui el código
        if (BuildVars.messageError != 0) {
            deleteMessageError();
        } */
        register(new Iniciar());
        register(new Detener());
        register(new RegistrarUsuario());
        register(new Accion());
        register(new Reglas());
        Ayuda helpCommand = new Ayuda(this);
        register(helpCommand);
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("El comando " + message.getText() + " no existe. Ejecuta el comando /ayuda para obtener ayuda.");
            BuildVars.messageError = message.getMessageId() + 2;
            BuildVars.chatMessageError = message.getChatId();
            try {
                absSender.execute(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        /*if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                echoMessage.setText("Hey heres your message:\n" + message.getText());

                try {
                    execute(echoMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        }*/
    }

    public void deleteMessageError() {
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(BuildVars.chatMessageError);
        delete.setMessageId(BuildVars.messageError);
        try {
            execute(delete);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
        BuildVars.messageError = 0;
        BuildVars.chatMessageError = 0;
    }

    @Override
    public String getBotToken() {
        return BotConfig.ROLESP_TOKEN;
    }
}