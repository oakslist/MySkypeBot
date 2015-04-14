package com.oakslist.tools.cmd;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Siarhei_Varachai on 4/13/2015.
 */
public class SkypeCommandPromt {

    public static String run(String argument, boolean waitForResponse) throws IOException {
        List<String> command = new ArrayList<String>();
        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);
        String shell;
        String response = "";
        if(osType.toString().equals("Windows")) {
            command.add("cmd.exe");
            command.add("/c");
        } else {
            shell = "/bin/bash";
            command.add(shell);
        }
        command.add(argument);
        System.out.println(Arrays.toString(command.toArray()));
        InputStream inputStream = null;
        InputStream errorStream = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            inputStream = process.getInputStream();
            errorStream = process.getErrorStream();

            if (waitForResponse) {
                // Wait for the shell to finish and get the return code
                int inputStreamExitStatus = process.waitFor();
                System.out.println("Exit status" + inputStreamExitStatus);
                response = convertStreamToStr(inputStream);
                // System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Error occured while executing Linux command. Error Description: "
                    + e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        }
        return response;
    }

    /*
    * To convert the InputStream to String we use the Reader.read(char[]
    * buffer) method. We iterate until the Reader return -1 which means
    * there's no more data to read. We use the StringWriter class to
    * produce the string.
    */
    public static String convertStreamToStr(InputStream is) throws IOException {

        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        }
        else {
            return "";
        }
    }
}
