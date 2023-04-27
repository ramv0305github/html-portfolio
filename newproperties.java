package com.walmart.sams.veda.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class newproperties {
    public WebDriver driver;

    public WebDriver WebDriverManager() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C://Users//vn54joi//supply-ingestion-automation//src//test//java//comwalmart//sams//veda//Selenium//globalproperties");
        prop.load(fis);
        String url = prop.getProperty("url");
        if (driver == null) {
            if(prop.getProperty("browser") == "edge")
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.get(url); }
        return driver;
    }
}