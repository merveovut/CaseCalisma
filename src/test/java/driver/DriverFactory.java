package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverFactory {

    public static WebDriver getDriver() {

        String browser = System.getenv("BROWSER");
        if (browser == null) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
        switch (browser)
        {
            case "IE":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }

        return null;
    }
}
