package com.dnastack.beacon.cli.beacon

import com.dnastack.beacon.cli.BaseBeaconCliTest
import com.dnastack.beacon.cli.commands.InfoCommand
import com.dnastack.beacon.cli.utils.GsonHelper
import org.ga4gh.beacon.Beacon

import static com.dnastack.beacon.cli.TestData.TEST_BEACON
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconSuccessTest extends BaseBeaconCliTest {
    @Override
    void setupMappings() {
        MOCK_BEACON_SERVER.stubFor(get(urlEqualTo("/"))

                .willReturn(aResponse()
                .withBody(GsonHelper.toJson(TEST_BEACON))))
    }

    @Override
    String[] getClientTestArguments() {
        return [InfoCommand.NAME]
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        def beacon = GsonHelper.fromJson(clientOutput, Beacon)

        assertThat(beacon).isEqualTo(TEST_BEACON)
        assertExitValueIsSuccessful(clientExitValue)
    }
}