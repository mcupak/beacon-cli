package com.dnastack.beacon.cli

import org.ga4gh.beacon.*

/**
 * Contains general test data.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class TestData {
    def public static final TEST_ORGANIZATION = new BeaconOrganization(
            id: "TestOrganization",
            name: "Test Organization",
    )

    def public static final TEST_DATASET = new BeaconDataset(
            id: "TestDataset",
            name: "Test Dataset",
            variantCount: 1000
    )

    def public static final TEST_BEACON = new Beacon(
            id: "TestBeacon",
            name: "Test Beacon",
            organization: TEST_ORGANIZATION,
            datasets: [TEST_DATASET]
    )

    def public static final TEST_DATASET_ALLELE_RESPONSE = new BeaconDatasetAlleleResponse(
            datasetId: "Dataset",
            exists: false,
            frequency: 0.5,
            variantCount: 10,
            info: ["Date_last_evaluated": "8/10/15"]
    )

    def public static final TEST_ALLELE_REQUEST = new BeaconAlleleRequest(
            referenceName: "1",
            start: 1000,
            referenceBases: "A",
            alternateBases: "T",
            assemblyId: "GRCh37",
            datasetIds: [TEST_DATASET_ALLELE_RESPONSE.datasetId],
            includeDatasetResponses: true
    )

    def public static final TEST_ALLELE_RESPONSE = new BeaconAlleleResponse(
            beaconId: "Beacon",
            exists: true,
            alleleRequest: TEST_ALLELE_REQUEST,
            datasetAlleleResponses: [TEST_DATASET_ALLELE_RESPONSE]
    )
}
