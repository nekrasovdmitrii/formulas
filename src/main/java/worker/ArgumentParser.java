package worker;

import data.ArgumentsData;
import helper.SignalObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ArgumentParser extends Thread {
    private String filePath;
    private ArgumentsData arguments;
    private SignalObject signalObj;

    public ArgumentParser(String filePath, ArgumentsData arguments, SignalObject signalObj) {
        this.filePath = filePath;
        this.arguments = arguments;
        this.signalObj = signalObj;
    }

    @Override
    public void run() {
        try {
            while (true) {
                signalObj.await();
                updateArguments();
            }
        } catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> parseArgument(String argsString) {

        return Arrays.stream(argsString.split(";"))
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(a -> a[0], a -> Integer.parseInt(a[1])));
    }

    private void updateArguments() throws FileNotFoundException {
        try(Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String argsString = scanner.nextLine();
                Map<String, Integer> args = parseArgument(argsString);
                arguments.update(args);
            }
        }

    }
}