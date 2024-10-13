package ro.xale.config;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "ro.xale.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports/report.html", "json:target/cucumber-reports/report.json"}
)
public class RunCucumber {
}
