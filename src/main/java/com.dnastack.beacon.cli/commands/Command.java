package com.dnastack.beacon.cli.commands;

import com.dnastack.beacon.cli.exceptions.ExecutionException;
import com.dnastack.beacon.client.BeaconClient;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.StringWriter;

/**
 * Represents a single beacon network cli command.
 * Command parameters get injected and then the command is ready to execute itself.
 * Each command has description and help message.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public abstract class Command {
    @Option(name = "-h", aliases = "--help", help = true)
    private Boolean showHelp;

    public String execute(BeaconClient beaconClient) throws ExecutionException {
        if (BooleanUtils.isTrue(showHelp)) {
            return getHelp();
        } else {
            return doExecute(beaconClient);
        }
    }

    protected abstract String doExecute(BeaconClient beaconClient) throws ExecutionException;

    protected String getHelp() {
        StringWriter help = new StringWriter();
        help.append(String.format("Description: %s\r\n", getDescription()));

        StringWriter usageWriter = new StringWriter();
        new CmdLineParser(this).printUsage(usageWriter, null);
        String usage = usageWriter.toString();
        if (StringUtils.isNotBlank(usage)) {
            help.append("Usage:\r\n");
            help.append(usage);
        }

        return help.toString();
    }

    public String getDescription() {
        return null;
    }
}
