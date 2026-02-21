package org.AmineSidki.runnable;

import org.AmineSidki.util.VersionUtil;
import picocli.CommandLine;

import java.time.LocalDateTime;

public class VersionRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|faint " + LocalDateTime.now() + "|@ @|bold,blue  INFO|@ --- @|magenta [Sprout]|@ : Sprout version v" + VersionUtil.version + " is installed !"));
    }
}
