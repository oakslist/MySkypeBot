package com.oakslist.tools.cmd;

/**
 * Created by Siarhei_Varachai on 4/13/2015.
 */
public class OsCheck {
    /**
     * Enum type which contains OS names.
     */
    private static OSType detectedOS;

    /**
     * <p>
     * Finds the OS
     * </p>
     *
     * @return One of the values of the enum OSType
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase();
            if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else {
                detectedOS = OSType.Linux;
            }
        }
        return detectedOS;
    }

    /**
     * Represents the popular os types i.e Windows, or MacOS or Linux
     */
    public enum OSType {
        Windows, MacOS, Linux
    }
}
