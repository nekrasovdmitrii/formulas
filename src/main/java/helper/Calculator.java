package helper;

import data.Token;
import exception.UnexpectedTokenException;

import java.util.List;
import java.util.Map;

public class Calculator {
    private Map<String, Integer> args;
    private List<Token> tokens;
    private String expression;
    private int currTokenPosition;

    public Calculator(String expression, List<Token> tokens, Map<String, Integer> args) {
        this.args = args;
        this.expression = expression;
        this.tokens = tokens;
    }

    public void calculate() {
        int res = expression(tokens);
        printResult(res);
    }

    private void printResult(int result) {
        System.out.println("--------------------");
        args.forEach((k,v) -> {
            System.out.print(k + "=" + v + " ");
        });
        System.out.println();
        System.out.println(expression + " = " + result);
        System.out.println("--------------------");
    }

    private int expression(List<Token> tokens) {
        int value = term(tokens);
        while (true) {
            Token token = tokens.get(currTokenPosition++);
            switch (token.getType()) {
                case OPERATION_PLUS:
                    value += term(tokens);
                    break;
                case OPERATION_MINUS:
                    value -= term(tokens);
                    break;
                case END:
                case CLOSE_BRACKET:
                    currTokenPosition--;
                    return value;
            }
        }
    }

    private int term(List<Token> tokens) {
        int value = factor(tokens);
        while (true) {
            Token token = tokens.get(currTokenPosition++);
            switch (token.getType()) {
                case OPERATION_MUL:
                    value *= factor(tokens);
                    break;
                case OPERATION_DIV:
                    value /= factor(tokens);
                    break;
                case END:
                case CLOSE_BRACKET:
                case OPERATION_PLUS:
                case OPERATION_MINUS:
                    currTokenPosition--;
                    return value;
                default:
                    throw new UnexpectedTokenException(token.getValue());
            }
        }
    }

    private int factor(List<Token> tokens) {
        Token token = tokens.get(currTokenPosition++);
        switch (token.getType()) {
            case NUMBER:
                return Integer.parseInt(token.getValue());
            case ARG:
                return args.get(token.getValue());
            case OPEN_BRACKET:
                return expression(tokens);
            default:
                throw new UnexpectedTokenException(token.getValue());
        }
    }
}
