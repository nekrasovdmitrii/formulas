package worker;

import helper.SignalObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class LastModifiedTimeChecker extends Thread {
    private static final int SLEEP_TIMEOUT = 5_000;
    private String filePath;
    private FileTime lastModifiedTime;
    private SignalObject signalObj;

    public LastModifiedTimeChecker(String filePath, SignalObject signalObj) {
        this.filePath = filePath;
        this.signalObj = signalObj;
    }

    @Override
    public void run() {
        try {
            while(true) {
                check();
                sleep(SLEEP_TIMEOUT);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void check() throws IOException {
        FileTime currModifiedTime = getCurrModifiedTime();
        if (lastModifiedTime == null || compareLastModifiedTime(currModifiedTime)) {
            lastModifiedTime = currModifiedTime;
            signalObj.signalAll();
        }

    }

    private FileTime getCurrModifiedTime() throws IOException {
        Path file = Paths.get(new File(filePath).toURI());
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        return attr.lastModifiedTime();
    }

    private boolean compareLastModifiedTime(FileTime currModifiedTime) {
        return lastModifiedTime.compareTo(currModifiedTime) < 0;
    }
}
