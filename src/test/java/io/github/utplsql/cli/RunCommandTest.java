package io.github.utplsql.cli;

import com.beust.jcommander.JCommander;
import io.github.utplsql.api.CustomTypes;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for run command.
 */
public class RunCommandTest {

    private RunCommand createCommand(String... args) {
        RunCommand runCmd = new RunCommand();

        JCommander.newBuilder()
                .addObject(runCmd)
                .args(args)
                .build();

        return runCmd;
    }

    @Test
    public void reporterOptions_Default() {
        RunCommand runCmd = createCommand("run", "app/app");

        List<ReporterOptions> reporterOptionsList = runCmd.getReporterOptionsList();

        ReporterOptions reporterOptions1 = reporterOptionsList.get(0);
        Assert.assertEquals(CustomTypes.UT_DOCUMENTATION_REPORTER, reporterOptions1.getReporterName());
        Assert.assertNull(reporterOptions1.getOutputFileName());
        Assert.assertFalse(reporterOptions1.outputToFile());
        Assert.assertTrue(reporterOptions1.outputToScreen());
    }

    @Test
    public void reporterOptions_OneReporter() {
        RunCommand runCmd = createCommand("run", "app/app", "-f=ut_documentation_reporter", "-o=output.txt");

        List<ReporterOptions> reporterOptionsList = runCmd.getReporterOptionsList();

        ReporterOptions reporterOptions1 = reporterOptionsList.get(0);
        Assert.assertEquals(CustomTypes.UT_DOCUMENTATION_REPORTER, reporterOptions1.getReporterName());
        Assert.assertEquals(reporterOptions1.getOutputFileName(), "output.txt");
        Assert.assertTrue(reporterOptions1.outputToFile());
        Assert.assertFalse(reporterOptions1.outputToScreen());
    }

    @Test
    public void reporterOptions_OneReporterForceScreen() {
        RunCommand runCmd = createCommand("run", "app/app", "-f=ut_documentation_reporter", "-o=output.txt", "-s");

        List<ReporterOptions> reporterOptionsList = runCmd.getReporterOptionsList();

        ReporterOptions reporterOptions1 = reporterOptionsList.get(0);
        Assert.assertEquals(CustomTypes.UT_DOCUMENTATION_REPORTER, reporterOptions1.getReporterName());
        Assert.assertEquals(reporterOptions1.getOutputFileName(), "output.txt");
        Assert.assertTrue(reporterOptions1.outputToFile());
        Assert.assertTrue(reporterOptions1.outputToScreen());
    }

    @Test
    public void reporterOptions_OneReporterForceScreenInverse() {
        RunCommand runCmd = createCommand("run", "app/app", "-f=ut_documentation_reporter", "-s", "-o=output.txt");

        List<ReporterOptions> reporterOptionsList = runCmd.getReporterOptionsList();

        ReporterOptions reporterOptions1 = reporterOptionsList.get(0);
        Assert.assertEquals(CustomTypes.UT_DOCUMENTATION_REPORTER, reporterOptions1.getReporterName());
        Assert.assertEquals(reporterOptions1.getOutputFileName(), "output.txt");
        Assert.assertTrue(reporterOptions1.outputToFile());
        Assert.assertTrue(reporterOptions1.outputToScreen());
    }

    @Test
    public void reporterOptions_TwoReporters() {
        RunCommand runCmd = createCommand("run", "app/app",
                "-f=ut_documentation_reporter",
                "-f=ut_coverage_html_reporter", "-o=coverage.html", "-s");

        List<ReporterOptions> reporterOptionsList = runCmd.getReporterOptionsList();

        ReporterOptions reporterOptions1 = reporterOptionsList.get(0);
        Assert.assertEquals(CustomTypes.UT_DOCUMENTATION_REPORTER, reporterOptions1.getReporterName());
        Assert.assertNull(reporterOptions1.getOutputFileName());
        Assert.assertFalse(reporterOptions1.outputToFile());
        Assert.assertTrue(reporterOptions1.outputToScreen());

        ReporterOptions reporterOptions2 = reporterOptionsList.get(1);
        Assert.assertEquals(CustomTypes.UT_COVERAGE_HTML_REPORTER, reporterOptions2.getReporterName());
        Assert.assertEquals(reporterOptions2.getOutputFileName(), "coverage.html");
        Assert.assertTrue(reporterOptions2.outputToFile());
        Assert.assertTrue(reporterOptions2.outputToScreen());
    }

}
