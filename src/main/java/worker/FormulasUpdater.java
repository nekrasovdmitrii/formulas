package worker;

import data.FormulasData;
import data.Token;
import helper.SignalObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FormulasUpdater extends Thread {
    private String filePath;
    private FormulasData formulas;
    private SignalObject signalObj;

    public FormulasUpdater(String filePath, FormulasData formulas, SignalObject signalObj) {
        this.filePath = filePath;
        this.formulas = formulas;
        this.signalObj = signalObj;
    }

    @Override
    public void run() {
        try {
            while (true) {
                signalObj.await();
                updateFormulas();
            }
        } catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateFormulas() throws FileNotFoundException {
        Map<String, List<Token>> map = new HashMap<>();
        try(Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String formula = scanner.nextLine();
                Tokenizer tokenizer = new Tokenizer(formula);
                map.put(formula, tokenizer.getTokens());
            }
        }

        formulas.update(map);
    }
}
