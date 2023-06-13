package worker;

import java.util.List;
import java.util.Map;
import data.FormulasData;
import data.ArgumentsData;
import data.Token;
import helper.Calculator;

public class CalculatorThread extends Thread {
    private static final int SLEEP_TIMEOUT = 1_000;
    private FormulasData formulas;
    private ArgumentsData argumentsData;

    public CalculatorThread(FormulasData formulas, ArgumentsData argumentsData) {
        this.formulas = formulas;
        this.argumentsData = argumentsData;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Map<String, Integer> args = argumentsData.get();
                if (args != null) {
                    Map<String, List<Token>> formulasMap = formulas.get();

                    formulasMap.forEach((expr, tokens) -> {
                        Calculator calculator = new Calculator(expr, tokens, args);
                        calculator.calculate();
                    });
                } else {
                    sleep(SLEEP_TIMEOUT);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
