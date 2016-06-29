package com.dnastack.beacon.cli.response

import com.dnastack.beacon.cli.BaseBeaconCliTest
import com.dnastack.beacon.cli.commands.ResponseCommand
import com.dnastack.beacon.cli.utils.GsonHelper
import org.ga4gh.beacon.BeaconAlleleResponse

import static com.dnastack.beacon.cli.TestData.TEST_ALLELE_REQUEST
import static com.dnastack.beacon.cli.TestData.TEST_ALLELE_RESPONSE
import static com.dnastack.beacon.client.BeaconRetroService.BEACON_REQUEST_PATH
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class AlleleResponseSuccessTest extends BaseBeaconCliTest {
    @Override
    void setupMappings() {
        MOCK_BEACON_SERVER.stubFor(post(urlPathEqualTo("/$BEACON_REQUEST_PATH"))
                .withRequestBody(equalToJson(GsonHelper.toJson(TEST_ALLELE_REQUEST)))

                .willReturn(aResponse()
                .withBody(GsonHelper.toJson(TEST_ALLELE_RESPONSE))))
    }

    @Override
    String[] getClientTestArguments() {
        return [ResponseCommand.NAME,
                ResponseCommand.REFERENCE_NAME_OPTION_KEY, TEST_ALLELE_REQUEST.referenceName,
                ResponseCommand.START_OPTION_KEY, TEST_ALLELE_REQUEST.start,
                ResponseCommand.REFERENCE_BASES_OPTION_KEY, TEST_ALLELE_REQUEST.referenceBases,
                ResponseCommand.ALTERNATE_BASES_OPTION_KEY, TEST_ALLELE_REQUEST.alternateBases,
                ResponseCommand.ASSEMBLY_ID_OPTION_KEY, TEST_ALLELE_REQUEST.assemblyId,
                ResponseCommand.DATASET_IDS_OPTION_KEY, TEST_ALLELE_REQUEST.datasetIds.join(","),
                ResponseCommand.INCLUDE_DATASET_RESPONSES_OPTION_KEY
        ]
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        def response = GsonHelper.fromJson(clientOutput, BeaconAlleleResponse)

        assertThat(response).isEqualTo(TEST_ALLELE_RESPONSE)
        assertExitValueIsSuccessful(clientExitValue)
    }
}
