package org.rolesp.admin.commands;

import org.rolesp.services.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Probar extends BotCommand {

    public static final String LOGTAG = "CREATERULESCOMMAND";

    public Probar() {
        super("probar", "Crear reglas de tu partida.");
    }

    @Override
    public void execute(AbsSender var1, User var2, Chat var3, Integer var4, String[] var5) {

    }
}
