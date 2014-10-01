package com.oakslist;

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

}
