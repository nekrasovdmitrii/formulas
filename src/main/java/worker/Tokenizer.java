package worker;

import data.Token;
import enums.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private List<Token> tokens = new ArrayList<>();
    private String inputExpression;

    public Tokenizer(String expression) {
        inputExpression = expression.toLowerCase();
    }

    public List<Token> getTokens() {
        int i = 0;
        while (i < inputExpression.length()) {
            char ch = inputExpression.charAt(i);
            switch (ch) {
                case '(':
                    tokens.add(new Token(TokenType.OPEN_BRACKET, ch));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.CLOSE_BRACKET, ch));
                    break;
                case '+':
                    tokens.add(new Token(TokenType.OPERATION_PLUS, ch));
                    break;
                case '-':
                    tokens.add(new Token(TokenType.OPERATION_MINUS, ch));
                    break;
                case '*':
                    tokens.add(new Token(TokenType.OPERATION_MUL, ch));
                    break;
                case '/':
                    tokens.add(new Token(TokenType.OPERATION_DIV, ch));
                    break;
                default:
                    if (Character.isDigit(ch)) {
                        i = processDigit(ch, i);
                    } else if (Character.isLetter(ch)) {
                        i = processLetters(ch, i);
                    }
            }

            i++;
        }

        tokens.add(new Token(TokenType.END, ""));
        return tokens;
    }

    private int processDigit(char ch, int currPosition) {
        StringBuilder numberBuilder = new StringBuilder();
        do {
            numberBuilder.append(ch);
            currPosition++;
            if (currPosition >= inputExpression.length()) {
                break;
            }
            ch = inputExpression.charAt(currPosition);
        } while (Character.isDigit(ch));

        currPosition--;
        tokens.add(new Token(TokenType.NUMBER, numberBuilder.toString()));

        return currPosition;
    }

    private int processLetters(char ch, int currPosition) {
        StringBuilder argBuilder = new StringBuilder();
        do {
            argBuilder.append(ch);
            currPosition++;
            if (currPosition >= inputExpression.length()) {
                break;
            }
            ch = inputExpression.charAt(currPosition);
        } while (Character.isLetter(ch) || Character.isDigit(ch));

        currPosition--;
        tokens.add(new Token(TokenType.ARG, argBuilder.toString()));

        return currPosition;
    }
}
