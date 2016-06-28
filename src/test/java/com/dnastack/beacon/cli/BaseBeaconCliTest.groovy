package com.dnastack.beacon.cli

import com.github.tomakehurst.wiremock.WireMockServer
import org.apache.commons.lang.StringUtils
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.assertj.core.api.Assertions.assertThat

/**
 * Helper class for CLI tests.
 * For each test, CLI is run in a separate process, its outputs are read and tested against the test checks.
 * When the java property beacon.test.url is not specified, the Beacon server is mocked, otherwise tests
 * are run against the specified Beacon (usually a real one).
 * Not all tests might support integration testing against a real Beacon server - this is defined by
 * {@link com.dnastack.beacon.cli.BaseBeaconCliTest#isIntegrationTestingSupported()}
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public abstract class BaseBeaconCliTest {
    static final def CLI_PROCESS_BUILDER_DEFAULTS = [
            "java",
            "-cp", System.getProperty("java.class.path"),
            Cli.class.getName(),
            CommandLine.BEACON_URL_OPTION_KEY
    ]
    static final def MOCK_BEACON_PORT = 8089
    static final def MOCK_BEACON_SERVER = new WireMockServer(wireMockConfig().port(MOCK_BEACON_PORT))
    static final def MOCKED_TESTING

    /**
     * Define if the testing will be against a real Beacon server, or the mocked one.
     */
    static {
        def beaconTestUrl = System.properties.getProperty("beacon.test.url")
        MOCKED_TESTING = StringUtils.isBlank(beaconTestUrl)
        CLI_PROCESS_BUILDER_DEFAULTS.add(MOCKED_TESTING ?
                new URL("http", "localhost", MOCK_BEACON_PORT, "").toString() :
                beaconTestUrl
        )
    }

    @BeforeSuite
    void startServer() {
        if (MOCKED_TESTING) {
            MOCK_BEACON_SERVER.start()
        }
    }

    @AfterSuite
    void stopServer() {
        if (MOCKED_TESTING) {
            MOCK_BEACON_SERVER.stop();
        }
    }

    @AfterMethod
    void resetMappings() {
        if (MOCKED_TESTING) {
            MOCK_BEACON_SERVER.resetMappings();
        }
    }

    @Test
    void test() {
        if (!MOCKED_TESTING && !isIntegrationTestingSupported()) {
            return
        }

        if (MOCKED_TESTING) {
            setupMappings()
        }
        def executionResult = executeClientAndCollectOutput()
        doTest(
                executionResult.clientOutput,
                executionResult.clientErrorOutput,
                executionResult.clientExitValue
        )
    }

    ExecutionResult executeClientAndCollectOutput() {
        def cliProcess = new ProcessBuilder(
                ((CLI_PROCESS_BUILDER_DEFAULTS as String[]) + getClientTestArguments()) as String[]
        ).start()

        def standardOutput = new StringBuilder()
        def standardErrorOutput = new StringBuilder()
        cliProcess.waitForProcessOutput(standardOutput, standardErrorOutput)

        return new ExecutionResult(
                clientOutput: standardOutput,
                clientErrorOutput: standardErrorOutput,
                clientExitValue: cliProcess.exitValue()
        )
    }

    void setupMappings() {}

    abstract String[] getClientTestArguments();

    boolean isIntegrationTestingSupported() { return true }

    abstract void doTest(String clientOutput, String clientErrorOutput, int clientExitValue);

    static void assertExitValueIsSuccessful(int clientExitValue) {
        assertThat(clientExitValue).isEqualTo(0)
    }

    static void assertExitValueIsError(int clientExitValue) {
        assertThat(clientExitValue).isEqualTo(1)
    }

    private static class ExecutionResult {
        String clientOutput
        String clientErrorOutput
        int clientExitValue
    }
}
