package com.dnastack.beacon.cli.exceptions;

import com.dnastack.beacon.cli.commands.Command;

/**
 * Thrown during {@link Command} execution.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class ExecutionException extends Exception {
    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
