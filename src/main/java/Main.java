import data.ArgumentsData;
import data.FormulasData;
import helper.SignalObject;
import worker.ArgumentParser;
import worker.CalculatorThread;
import worker.FormulasUpdater;
import worker.LastModifiedTimeChecker;

public class Main {
    private static final String FORMULAS_PATH = "formulas.txt";
    private static final String ARGS_PATH = "args.txt";

    public static void main(String[] args) {
        SignalObject formulasSignalObj = new SignalObject();
        SignalObject argsSignalObj = new SignalObject();
        FormulasData formulas = new FormulasData();
        ArgumentsData arguments = new ArgumentsData();

        new FormulasUpdater(FORMULAS_PATH, formulas, formulasSignalObj).start();
        new LastModifiedTimeChecker(FORMULAS_PATH, formulasSignalObj).start();

        new ArgumentParser(ARGS_PATH, arguments, argsSignalObj).start();
        new LastModifiedTimeChecker(ARGS_PATH, argsSignalObj).start();

        new CalculatorThread(formulas, arguments).start();
    }
}
