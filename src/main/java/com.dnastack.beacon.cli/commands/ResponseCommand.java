package com.dnastack.beacon.cli.commands;

import com.dnastack.beacon.cli.exceptions.ExecutionException;
import com.dnastack.beacon.cli.utils.Gson;
import com.dnastack.beacon.client.BeaconClient;
import com.dnastack.beacon.client.exceptions.InternalException;
import org.ga4gh.beacon.BeaconAlleleResponse;
import org.kohsuke.args4j.Option;

import java.util.List;

/**
 * Holds parameters and actually queries the beacon.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class ResponseCommand extends Command {
    public static final String NAME = "response";

    public static final String REFERENCE_NAME_OPTION_KEY = "-r";
    public static final String START_OPTION_KEY = "-s";
    public static final String REFERENCE_BASES_OPTION_KEY = "-rb";
    public static final String ALTERNATE_BASES_OPTION_KEY = "-ab";
    public static final String ASSEMBLY_ID_OPTION_KEY = "-a";
    public static final String DATASET_IDS_OPTION_KEY = "-d";
    public static final String INCLUDE_DATASET_RESPONSES_OPTION_KEY = "-i";

    private final String REFERENCE_NAME_USAGE = "Reference Name (chromosome). Accepted values: 1-22, X, Y.";
    private final String START_USAGE = "Position, allele locus (0-based). Accepted values: non-negative integers smaller than reference " +
            "length.";
    private final String REFERENCE_BASES_USAGE = "Reference Bases for this variant (starting from `start`). Accepted values: see the REF field in " +
            "VCF 4.2 specification (https://samtools.github.io/hts-specs/VCFv4.2.pdf).";
    private final String ALTERNATE_BASES_USAGE = "The bases that appear instead of the reference bases. Accepted values: see the ALT field in " +
            "VCF 4.2 specification (https://samtools.github.io/hts-specs/VCFv4.2.pdf).";
    private final String ASSEMBLY_ID_USAGE = "Assembly ID (GRC notation, e.g. 'GRCh37').";
    private final String DATASET_IDS_USAGE = "IDs of datasets. If null, all datasets will be queried.";
    private final String INCLUDE_DATASET_RESPONSES_USAGE = "If responses for individual datasets should be included in the response (default = false).";

    @Option(name = REFERENCE_NAME_OPTION_KEY, aliases = "--reference", required = true,
            usage = REFERENCE_NAME_USAGE)
    private String referenceName;

    @Option(name = START_OPTION_KEY, aliases = "--start", required = true,
            usage = START_USAGE)
    private long start;

    @Option(name = REFERENCE_BASES_OPTION_KEY, aliases = "--reference-bases", required = true,
            usage = REFERENCE_BASES_USAGE)
    private String referenceBases;

    @Option(name = ALTERNATE_BASES_OPTION_KEY, aliases = "--alternate-bases", required = true,
            usage = ALTERNATE_BASES_USAGE)
    private String alternateBases;

    @Option(name = ASSEMBLY_ID_OPTION_KEY, aliases = "--assembly-id", required = true,
            usage = ASSEMBLY_ID_USAGE)
    private String assemblyId;

    @Option(name = DATASET_IDS_OPTION_KEY, aliases = "--dataset-ids", usage = DATASET_IDS_USAGE)
    private List<String> datasetIds;

    @Option(name = INCLUDE_DATASET_RESPONSES_OPTION_KEY, aliases = "--include-dataset-responses",
            usage = INCLUDE_DATASET_RESPONSES_USAGE)
    private boolean includeDatasetResponses;

    @Override
    public String doExecute(BeaconClient beaconClient) throws ExecutionException {
        BeaconAlleleResponse response = getBeaconResponse(beaconClient);
        return Gson.pretty(response);
    }

    private BeaconAlleleResponse getBeaconResponse(BeaconClient beaconClient) throws ExecutionException {
        try {
            return beaconClient.getBeaconAlleleResponse(referenceName, start, referenceBases, alternateBases, assemblyId,
                    datasetIds, includeDatasetResponses);
        } catch (InternalException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String getDescription() {
        return "Gets response to a beacon query for allele information.";
    }
}
