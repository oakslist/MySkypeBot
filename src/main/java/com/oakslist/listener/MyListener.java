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

import java.io.IOException;
import java.util.Map;

//TODO check why I send double messages
// and set PC name to message


/**
 * Created by Siarhei_Varachai on 10/1/2014.
 */
public class MyListener implements ChatMessageListener {

    private static final String passRequest = "Send me your password, please...";

    private Map queriesHashMap;
    private String lastMessage = "";
    private String passwordMessage = "";
    private String commandMessage = "";

    // waiting password from user
    private boolean isWaitPassword = false;

    // create bot variables
    private ChatterBotFactory myFactory;
    private ChatterBot skypeBot;
    private ChatterBotSession skypeSession;

    public MyListener() {
        // create an user query map
        LoadQueriesMap loadQueriesMap = new LoadQueriesMap();
        this.queriesHashMap = loadQueriesMap.getHashMap();
        // create bot interface
        try {
            this.myFactory = new ChatterBotFactory();
            this.skypeBot = this.myFactory.create(ChatterBotType.JABBERWACKY);
            this.skypeSession = this.skypeBot.createSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void myListener(String myMessage, Chat myChat) throws Exception {
        // 2 sec wait
        Thread.sleep(2000);

        System.out.println("--------------------------------\n point 0-0");

        try {
            final Chat chatterup = myChat;

            String msg = myMessage.toString();
            String[] parts = msg.split(" ");
            //TODO clean debugger msg
            System.out.println("\n point 0-0");

            if (parts[0].equals("getip") || parts[0].equals("cmd")) {
                this.isWaitPassword = true;
                setLastMessage(parts[0]);
                if (parts[0].equals("cmd") || parts[1] != null || parts[1] != "") {
                    String fullCommand = "";
                    for(int i = 1; i < parts.length - 1; i++) {
                        fullCommand = fullCommand + parts[i] + " ";
                    }
                    fullCommand = fullCommand + parts[parts.length - 1];
                    setCommandMessage(fullCommand);
                }
                chatterup.send(passRequest);
            } else {
                // check if we are waiting a password from user (after query was generated)
                if (this.isWaitPassword) {
                    // the password is correct
                    if (parts[0].equals(LoadPropertiesFile.pass)) {
                        switch (getLastMessage()) {
                            case "getip":
                                // send current ip address
                                LoadIp myIp = new LoadIp();
                                String currentIp = myIp.getIp();
                                chatterup.send(currentIp);
                                break;
                            case "cmd":
                                // create cmd command
                                chatterup.send("you in cmd line!!!\n");
                                try {
                                    Runtime.getRuntime().exec("cmd.exe /c start " + getCommandMessage());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                setCommandMessage("");
                                break;
                            default:
                                // the password is incorrect
                                chatterup.send("password incorrect!\n" + passRequest);
                        }
                        this.isWaitPassword = false;
                    } else {
                        // close query without response
                        if (parts[0].equals("close")) {
                            this.isWaitPassword = false;
                            chatterup.send("closed.");
                        } else {
                            // the password is incorrect
                            chatterup.send("password incorrect!\n" + passRequest);
                        }
                    }
                } else {
                    // just answers from our bot
                    myMessage = skypeSession.think(myMessage);
                    chatterup.send(myMessage);
                }
            }
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        }
    }

    // receive messages to bot from user
    @Override
    public void chatMessageReceived(ChatMessage recMessage) throws SkypeException {
        try {
            // check people which need to be answered
//            || recMessage.getSenderDisplayName().toString().equals("oaksbot")
            if (recMessage.getSenderDisplayName().toString().equals("Siarhei Varachai")
                    || recMessage.getSenderDisplayName().toString().equals("Aliaksandr Varachai")) {
                myListener(recMessage.getContent(), recMessage.getChat());
                //TODO clean debugger msg
                System.out.println("\n point 1-1");
            }
            // for console display of skype interaction
            System.out.println("\n get from " + recMessage.getSenderDisplayName() + " : " + recMessage.getContent());
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        }  catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    // send messages from bot to user
    @Override
    public void chatMessageSent(ChatMessage sentMessage) throws SkypeException {
        try {
            //next line for show the bot conversation with the same bot =)
//            myListener(sentMessage.getContent(), sentMessage.getChat());
            //TODO clean debugger msg
            System.out.println("\n point 2-2");
            System.out.println("\n sent from " + sentMessage.getSenderDisplayName() + " : " + sentMessage.getContent());
        } catch (final SkypeException ex) {
            ex.printStackTrace();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getLastMessage() {
        return this.lastMessage;
    }

    private void setLastMessage(String message) {
        this.lastMessage = message;
    }

    private String getPasswordMessage() {
        return this.passwordMessage;
    }

    private void setPasswordMessage(String password) {
        this.passwordMessage = password;
    }

    private String getCommandMessage() {
        return this.commandMessage;
    }

    private void setCommandMessage(String command) {
        this.commandMessage = command;
    }

}

