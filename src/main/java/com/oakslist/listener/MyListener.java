package com.oakslist.listener;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.oakslist.load.LoadIp;
import com.oakslist.load.LoadPropertiesFile;
import com.oakslist.load.LoadQueriesMap;
import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//TODO check why I send double messages
// and set PC name to message


/**
 * Created by Siarhei_Varachai on 10/1/2014.
 */
public class MyListener implements ChatMessageListener {

    private static final String passRequest = "Hi! Send me your password, please...";

    private Map queriesHashMap;
    private StringBuilder lastMessage = new StringBuilder();

    public MyListener() {
        LoadQueriesMap loadQueriesMap = new LoadQueriesMap();
        this.queriesHashMap = loadQueriesMap.getHashMap();
    }

    private void myListener(String myMessage, Chat myChat) throws Exception {
        ChatterBotFactory myFactory = new ChatterBotFactory();
        ChatterBot skypeBot = myFactory.create(ChatterBotType.JABBERWACKY);
        ChatterBotSession skypeSession = skypeBot.createSession();

        Thread.sleep(3000);

        try {
            final Chat chatterup = myChat;

            if (myMessage.toString().equals("getip")
                    || myMessage.toString().equals("Hi there.  Have we met somewhere?  I think I recognize your face.")) {
                if (getLastMessage().toString().equals(LoadPropertiesFile.pass)) {
                    setLastMessage("");
                    LoadIp myIp = new LoadIp();
                    String currentIp = myIp.getIp();
                    chatterup.send(currentIp);
                } else {
                    setLastMessage(myMessage.toString());
                    chatterup.send(passRequest);
                }
            } else {
                setLastMessage("");
                myMessage = skypeSession.think(myMessage);
                chatterup.send(myMessage);
            }
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void chatMessageReceived(ChatMessage recMessage) throws SkypeException {
        try {

            // check people which need to be answered
            if (recMessage.getSenderDisplayName().equals("aaa")//) {
                    || recMessage.getSenderDisplayName().equals("Aliaksandr Varachai")) {
                myListener(recMessage.getContent(), recMessage.getChat());
            }

            // for console display of skype interaction
            System.out.println("\n" + recMessage.getSenderDisplayName() + " : " + recMessage.getContent());
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        }  catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void chatMessageSent(ChatMessage sentMessage) throws SkypeException {
        try {
            //next line for show the bot conversation with the same bot =)
//            myListener(sentMessage.getContent(), sentMessage.getChat());
            System.out.println("\n" + sentMessage.getSenderDisplayName() + " : " + sentMessage.getContent());
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public StringBuilder getLastMessage() {
        return this.lastMessage;
    }

    public void setLastMessage(String message) {
        this.lastMessage.setLength(0);
        this.lastMessage.append(message);
    }

}

