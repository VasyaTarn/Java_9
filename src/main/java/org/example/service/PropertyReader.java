package org.example.service;

import org.example.exception.PropertyFileException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.getProperty;

public class PropertyReader {
    public Properties readProperties() throws PropertyFileException
    {
        Properties property = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties"))
        {
            property.load(fileInputStream);
            return property;
        }
        catch (IOException e)
        {
            throw new PropertyFileException("Error open file property");
        }
    }
}
