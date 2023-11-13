package driver;

import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class Driver {

    public static WebDriver webDriver;

    @BeforeScenario
    public void beforeScenario(){
        // READ BROWSER env > default.properties -> If you dont choose, default run browser Chrome
        webDriver = DriverFactory.getDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(75));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterStep
    public void afterStep(){
        // Screenshot default path reports -> images
        Gauge.captureScreenshot();
    }

    @AfterScenario
    public void closeDriver(){
        webDriver.close();
    }

}

