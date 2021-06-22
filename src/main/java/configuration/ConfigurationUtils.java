package configuration;

import java.io.File;

public class ConfigurationUtils{
    private static final String driversDir = "src/test/resources/drivers/";
    private static final String chromedriverName = "chromedriver.exe";
    private static final String geckodriverName = "geckodriver.exe";


    public static String getChromeDriverPath(){
        return new File(driversDir + chromedriverName).getAbsolutePath();
    }

    public static String getGeckoDriverPath(){
        return new File(driversDir + geckodriverName).getAbsolutePath();
    }
}