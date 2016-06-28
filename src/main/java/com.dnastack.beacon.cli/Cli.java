package com.dnastack.beacon.cli;

import com.dnastack.beacon.cli.exceptions.ExecutionException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Beacon CLI.
 * Depending on the parsed command line arguments, CLI prints help message or executes requested commands by delegating
 * the request to corresponding command processors.
 * As well as "-h", empty command list is treated as help request.
 * Successful messages are written to standard output stream; cli exists with code 0.
 * Error messages are written to standard error stream; cli exists with code 1.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class Cli {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            printMainHelp();
            exitWithError();
        } else {
            CommandLine commandLine = parseCommandLineArguments(args);
            String result = executeCommands(commandLine);
            exitWithSuccessfulResult(result);
        }
    }

    private static void printMainHelp() {
        CommandLine helpRequestedCommandLine = CommandLine.builder().showHelp(true).build();
        String help = executeCommands(helpRequestedCommandLine);
        System.out.println(help);
    }

    private static CommandLine parseCommandLineArguments(String[] args) {
        CommandLine commandLine = new CommandLine();
        try {
            new CmdLineParser(commandLine).parseArgument(args);
        } catch (CmdLineException e) {
            exitWithError(e.getMessage());
        }
        return commandLine;
    }

    private static String executeCommands(CommandLine commandLine) {
        try {
            return commandLine.execute();
        } catch (ExecutionException e) {
            exitWithError(e.getMessage());
        }

        // will never reach here (exits on error)
        return null;
    }

    private static void exitWithError(String message) {
        System.err.println(message);
        exitWithError();
    }

    private static void exitWithError() {
        System.exit(1);
    }

    private static void exitWithSuccessfulResult(String result) {
        System.out.println(result);
        exitWithSuccessfulResult();
    }

    private static void exitWithSuccessfulResult() {
        System.exit(0);
    }
}
