package org.rolesp.admin.commands;

import org.rolesp.game.Preferencias;
import org.rolesp.game.Reglas;
import org.rolesp.game.Usuario;
import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

/**
 * Este comando inicia la partida con el bot
 *
 * @author SirVaro
 */
public class Iniciar extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";
    public static Preferencias preferences = new Preferencias();
    public static Reglas reglas = new Reglas();
    public static Usuario usuario = new Usuario();

    public Iniciar() {
        super("iniciar", "Iniciar el juego y cargar datos.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        SendMessage answer = new SendMessage();
        DeleteMessage delete = new DeleteMessage();
        if (Usuario.usuarios.size() == 0) {
            usuario.cargar();
            boolean administrador = false;
            for (int i = 0; i < Usuario.usuarios.size(); i++) {
                if (Usuario.usuarios.get(i).getId() == user.getId() & Usuario.usuarios.get(i).getPermisos().equalsIgnoreCase("Administrador")) {
                    administrador = true;
                    break;
                }
            }
            if (administrador) {
                if (preferences.cargar()) {
                    /* Métodos para cargar lo demás */
                    reglas.cargar();
                    answer.setReplyMarkup(KeyboardBuilder.createInline()
                            .addRow(row -> row
                                    .addButton("button 1", "btn 1")
                                    .addButton("button 2", "btn 2"))
                            .addRow(row -> row
                                    .addButton("button 3", "btn 3"))
                            .build()
                    );
                    answer.setChatId(chat.getId().toString());
                    answer.setText("¿Que quieres hacer?");
                } else {
                    messageBuilder.append("No existe ninguna partida.\n").append("Para crearla, utiliza el comando /crear_juego");
                    answer.setChatId(chat.getId().toString());
                    answer.setText(messageBuilder.toString());
                }
            } else {
                answer.setChatId(chat.getId().toString());
                answer.setText("No eres administrador");
            }
        } else {
            answer.setChatId(chat.getId().toString());
            answer.setText("El juego ya esta iniciado");
        }
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