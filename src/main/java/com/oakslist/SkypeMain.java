package com.oakslist;

import com.oakslist.listener.MyListener;
import com.oakslist.load.LoadPropertiesFile;
import com.skype.Skype;
import com.skype.SkypeException;

import java.io.*;
import java.util.Properties;

/**
 * Created by Siarhei_Varachai on 10/1/2014.
 */
public class SkypeMain {

    public static void main(String[] args) throws SkypeException {

        new SkypeMain();

        Skype.addChatMessageListener(new MyListener());
        Skype.setDeamon(false);
    }

    public SkypeMain() {
        new LoadPropertiesFile();
    }

}
