package org.example.configuration;

import java.io.File;

public class ConfigurationUtils{
    private static final String driversDir = "src/test/resources/drivers/";
    private static final String chromedriverName = "chromedriver.exe";

    public static String getChromeDriverPath(){
        return new File(driversDir + chromedriverName).getAbsolutePath();
    }
}