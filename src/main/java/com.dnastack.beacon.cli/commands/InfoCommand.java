package com.dnastack.beacon.cli.commands;

import com.dnastack.beacon.cli.exceptions.ExecutionException;
import com.dnastack.beacon.cli.utils.Gson;
import com.dnastack.beacon.client.BeaconClient;
import com.dnastack.beacon.client.exceptions.InternalException;
import org.ga4gh.beacon.Beacon;

/**
 * Holds parameters and actually gets the beacon information.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class InfoCommand extends Command {
    public static final String NAME = "info";

    @Override
    public String doExecute(BeaconClient beaconClient) throws ExecutionException {
        Beacon beacon = getBeacon(beaconClient);
        return Gson.pretty(beacon);
    }

    private Beacon getBeacon(BeaconClient beaconClient) throws ExecutionException {
        try {
            return beaconClient.getBeacon();
        } catch (InternalException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String getDescription() {
        return "Gets beacon information.";
    }
}
