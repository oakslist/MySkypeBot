package com.oakslist.tools.load;

import com.oakslist.SkypeMain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Siarhei_Varachai on 10/2/2014.
 */
public class LoadPropertiesFile {

    private static final String PROP_FILE_NAME = "config.properties";
    private static final String PROP_PASS_NAME = "pass";

    private static boolean isLoaded = false;
    private static Properties prop;
    public static String pass = "";

    public LoadPropertiesFile() {
        if (!isLoaded) {
            loadPass(PROP_FILE_NAME);
        }
    }

    public Properties getPropFile() {
        return this.prop;
    }

    private void loadPass(String propFileName) {
        this.prop = new Properties();
        InputStream input = null;

        try {
            String filename = PROP_FILE_NAME;
            input = SkypeMain.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            this.prop.load(input);

            //get the property value and print it out
            this.pass = this.prop.getProperty(PROP_PASS_NAME);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
