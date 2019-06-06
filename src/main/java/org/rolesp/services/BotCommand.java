package org.rolesp.services;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class BotCommand implements IBotCommand {
    public static final String COMMAND_INIT_CHARACTER = "/";
    public static final String COMMAND_PARAMETER_SEPARATOR_REGEXP = "\\s+";
    private static final int COMMAND_MAX_LENGTH = 32;
    private final String commandIdentifier;
    private final String description;

    public BotCommand(String commandIdentifier, String description) {
        if (commandIdentifier != null && !commandIdentifier.isEmpty()) {
            if (commandIdentifier.startsWith("/")) {
                commandIdentifier = commandIdentifier.substring(1);
            }

            if (commandIdentifier.length() + 1 > 32) {
                throw new IllegalArgumentException("commandIdentifier cannot be longer than 32 (including /)");
            } else {
                this.commandIdentifier = commandIdentifier.toLowerCase();
                this.description = description;
            }
        } else {
            throw new IllegalArgumentException("commandIdentifier for command cannot be null or empty");
        }
    }

    public final String getCommandIdentifier() {
        return this.commandIdentifier;
    }

    public final String getDescription() {
        return this.description;
    }

    public String toString() {
        return "<b>/" + this.getCommandIdentifier() + "</b>\n" + this.getDescription();
    }

    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        this.execute(absSender, message.getFrom(), message.getChat(), message.getMessageId(), arguments);
    }

    public abstract void execute(AbsSender var1, User var2, Chat var3, Integer var4, String[] var5);
}
