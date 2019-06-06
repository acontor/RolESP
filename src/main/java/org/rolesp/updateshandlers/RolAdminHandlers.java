package org.rolesp.updateshandlers;

import org.rolesp.BotConfig;
import org.rolesp.BuildVars;
import org.rolesp.admin.commands.*;
import org.rolesp.game.Preferencias;
import org.rolesp.game.Reglas;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class RolAdminHandlers extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";

    /**
     * Constructor.
     */
    public RolAdminHandlers(String botUsername) {
        super(botUsername);
        /* Nunca pasa por aqui el código
        if (BuildVars.messageError != 0) {
            deleteMessageError();
        } */

        register(new Iniciar());
        Ayuda helpCommand = new Ayuda(this);
        register(helpCommand);
        register(new CrearJuego());
        register(new Probar());
        register(new CrearReglas());
        register(new RegistrarAdministrador());
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("El comando " + message.getText() + " no existe. Ejecuta el comando /ayuda para obtener ayuda.");
            BuildVars.messageError = message.getMessageId() + 1;
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
        if (BuildVars.messageError != 0) {
            deleteMessageError();
        }
        if (CrearReglas.crearReglas == 1) {
            crearReglas(update);
        }
        if (CrearJuego.crearJuego > 0) {
            crearJuego(update);
            if (CrearJuego.crearJuego != 0) {
                CrearJuego.crearJuego++;
            }
        }
        if(update.hasCallbackQuery()) {
            System.out.println(update.getCallbackQuery().getData());
        }
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

    public void crearJuego(Update update) {
        SendMessage send = new SendMessage();
        DeleteMessage delete = new DeleteMessage();
        DeleteMessage delete2 = new DeleteMessage();
        switch (CrearJuego.crearJuego) {
            case 1:
                Preferencias.setNombre(update.getMessage().getText());
                send.setChatId(update.getMessage().getChatId());
                send.setText("¿De que trata la historia?");
                delete.setChatId(update.getMessage().getChatId());
                delete.setMessageId(update.getMessage().getMessageId());
                delete2.setChatId(update.getMessage().getChatId());
                delete2.setMessageId(update.getMessage().getMessageId() - 1);
                try {
                    execute(delete);
                    execute(send);
                    execute(delete2);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
                break;
            case 2:
                Preferencias.setHistoria(update.getMessage().getText());
                Preferencias.setEstado("Creado");
                send.setChatId(update.getMessage().getChatId());
                send.setText("Tu partida se ha creado con éxito. Para añadir las reglas utiliza el comando /crear_reglas");
                delete.setChatId(update.getMessage().getChatId());
                delete.setMessageId(update.getMessage().getMessageId());
                delete2.setChatId(update.getMessage().getChatId());
                delete2.setMessageId(update.getMessage().getMessageId() - 1);
                Preferencias preferences = new Preferencias();
                preferences.insertar();
                try {
                    execute(delete);
                    execute(send);
                    execute(delete2);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
                CrearJuego.crearJuego = 0;
        }
    }

    private void crearReglas(Update update) {
        Reglas reglas = new Reglas();
        reglas.setReglas(update.getMessage().getText());
        reglas.insertar();
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(update.getMessage().getChatId());
        delete.setMessageId(update.getMessage().getMessageId());
        DeleteMessage delete2 = new DeleteMessage();
        delete2.setChatId(update.getMessage().getChatId());
        delete2.setMessageId(update.getMessage().getMessageId() - 1);
        SendMessage send = new SendMessage();
        send.setChatId(update.getMessage().getChatId());
        send.setText("Reglas creadas. Para verlas, dirígete al grupo del bot y utiliza el comando /reglas");
        try {
            execute(delete);
            execute(send);
            execute(delete2);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    @Override
    public String getBotToken() {
        return BotConfig.ROLESPADMIN_TOKEN;
    }
}