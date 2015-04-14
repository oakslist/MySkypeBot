package com.oakslist;

import com.oakslist.listener.MyListener;
import com.oakslist.tools.load.LoadPropertiesFile;
import com.skype.Skype;
import com.skype.SkypeException;

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
