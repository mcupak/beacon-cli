package com.dnastack.beacon.cli.help

import com.dnastack.beacon.cli.BaseBeaconCliTest

import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class NoArgumentsTest extends BaseBeaconCliTest {
    @Override
    String[] getClientTestArguments() {
        return []
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        assertThat(clientErrorOutput).isNotEmpty() // check some help is printed
        assertExitValueIsError(clientExitValue)
    }
}