package org.rolesp.admin.commands;

import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

/**
 * This command helps the user to find the command they need
 *
 * @author Timo Schulz (Mit0x2)
 */
public class Ayuda extends BotCommand {

    private static final String LOGTAG = "HELPCOMMAND";

    private final ICommandRegistry commandRegistry;

    public Ayuda(ICommandRegistry commandRegistry) {
        super("ayuda", "Todos los comandos disponibles");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] strings) {

        StringBuilder helpMessageBuilder = new StringBuilder();

        for (IBotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            helpMessageBuilder.append(botCommand.toString()).append("\n\n");
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(chat.getId().toString());
        delete.setMessageId(messageId);

        try {
            absSender.execute(delete);
            absSender.execute(helpMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
