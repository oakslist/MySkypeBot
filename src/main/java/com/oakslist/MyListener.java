package com.oakslist;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Siarhei_Varachai on 10/1/2014.
 */
public class MyListener implements ChatMessageListener {

    private void myListener(String myMessage, Chat myChat) throws Exception {
        ChatterBotFactory myFactory = new ChatterBotFactory();
        ChatterBot skypeBot = myFactory.create(ChatterBotType.JABBERWACKY);
        ChatterBotSession skypeSession = skypeBot.createSession();

        try {
            final Chat chatterup = myChat;
            
            if (myMessage.toString().equals("getip")) {
                String currentIp = getIp();
                System.out.println(currentIp);
                chatterup.send(currentIp);
                System.out.println("1st loc");
            } else {
                myMessage = skypeSession.think(myMessage);
                System.out.println("2nd loc");
                chatterup.send(myMessage);
                System.out.println("3rd loc");
            }
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void chatMessageReceived(ChatMessage recMessage) throws SkypeException {
        try {
            myListener(recMessage.getContent(), recMessage.getChat());

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
            myListener(sentMessage.getContent(), sentMessage.getChat());
            System.out.println("\n" + sentMessage.getSenderDisplayName() + " : " + sentMessage.getContent());
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        String ip = "No Ip";
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            ip = in.readLine();
            return ip;
        } catch (Exception ex) {

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ip;
    }
}

