package org.rolesp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuildVars {

    public static final List<Integer> ADMINS = new ArrayList<>();

    public static final String pathToLogs = "./";

    public static final File gameInfo = new File("savedata/gameinfo,dat");

    public static int messageError = 0;

    public static long chatMessageError = 0;


    static {
        // Add elements to ADMIN array
    }
}
