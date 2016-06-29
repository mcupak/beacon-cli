package com.dnastack.beacon.cli;


import com.dnastack.beacon.cli.commands.Command;
import com.dnastack.beacon.cli.commands.InfoCommand;
import com.dnastack.beacon.cli.commands.ResponseCommand;
import com.dnastack.beacon.cli.exceptions.ExecutionException;
import com.dnastack.beacon.client.BeaconClient;
import com.dnastack.beacon.client.BeaconClientImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Builder;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

import java.io.StringWriter;

/**
 * Represents total parsed command line.
 * It delegates its execution to the actual command instances.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@AllArgsConstructor
@Builder
@Data
@RequiredArgsConstructor
public class CommandLine {
    public static final String DEFAULT_BEACON_URL = "https://localhost:5000/";

    public static final String BEACON_URL_OPTION_KEY = "-u";

    @Argument(handler = SubCommandHandler.class, metaVar = "Main Command", required = true, usage = "Use one of " +
            "these commands. To get help on each command, Type -h with the corresponding command.")
    @SubCommands({
            @SubCommand(name = InfoCommand.NAME, impl = InfoCommand.class),
            @SubCommand(name = ResponseCommand.NAME, impl = ResponseCommand.class)
    })
    private Command command;

    @Option(name = BEACON_URL_OPTION_KEY, aliases = "--url",
            usage = "Beacon URL (default = " + DEFAULT_BEACON_URL + ")")
    private String beaconUrl;

    @Option(name = "-h", aliases = "-^-help", help = true)
    private Boolean showHelp;

    public String execute() throws ExecutionException {
        if (BooleanUtils.isTrue(showHelp)) {
            return getHelp();
        } else {
            return doExecute();
        }
    }

    private String doExecute() throws ExecutionException {
        String beaconBaseUrl = StringUtils.isNotBlank(beaconUrl) ? beaconUrl : DEFAULT_BEACON_URL;
        BeaconClient beaconNetworkClient = new BeaconClientImpl(beaconBaseUrl);
        return command.execute(beaconNetworkClient);
    }

    private String getHelp() {
        StringWriter help = new StringWriter();
        help.append("Beacon CLI Usage:\r\n");
        new CmdLineParser(this).printUsage(help, null);
        return help.toString();
    }
}
