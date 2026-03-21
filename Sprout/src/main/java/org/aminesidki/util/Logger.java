package org.aminesidki.util;

import picocli.CommandLine;

import java.time.LocalDateTime;

/**
 * Provides properly formatted info messages
 */
public class Logger {
    private static Logger instance;

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    public String infoMessage(String message){
        return CommandLine.Help.Ansi.AUTO.string("@|faint " + LocalDateTime.now() + "|@ @|bold,blue  INFO|@ --- @|magenta [Sprout]|@ : " + message);
    }
}
