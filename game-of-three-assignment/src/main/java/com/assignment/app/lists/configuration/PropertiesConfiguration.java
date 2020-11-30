package com.assignment.app.lists.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


public class PropertiesConfiguration {
	
	private static final Logger THELOGGER = LoggerFactory.getLogger(PropertiesConfiguration.class);
	
	 // Store properties info.

    private static Properties propinfo = new Properties();

    public static Properties getProperties() {
        return propinfo;
    }

    private PropertiesConfiguration() {
    }

    // Initialize properties file.
     
    public static void init(String file) {
        loadFile(file);
    }

    private static void loadFile(String file) {

        try(InputStream in = PropertiesConfiguration.class.getClassLoader().getResourceAsStream(file)) {
            if (Objects.isNull(in)) throw new FileNotFoundException();
            propinfo.load(in);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException( "File " + file + " not found");
        } catch(IOException er) {
            throw new RuntimeException("Could not read the file: " + file );
        }

        THELOGGER.info("File found ", file);
    }
	

}
