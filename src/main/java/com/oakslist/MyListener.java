package com.oakslist;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;

import java.net.SocketPermission;

/**
 * Created by Siarhei_Varachai on 10/1/2014.
 */
public class MyListener implements ChatMessageListener {

    private void myListener(String myMessage, Chat myChat) throws Exception {
        ChatterBotFactory myFactory = new ChatterBotFactory();
        ChatterBot skypeBot = myFactory.create(ChatterBotType.JABBERWACKY);
        ChatterBotSession skypeSession = skypeBot.createSession();

        try {
            myMessage = skypeSession.think(myMessage);
            final Chat chatterup = myChat;
            chatterup.send(myMessage);
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
}

