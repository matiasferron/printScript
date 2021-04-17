package statement.parsers.expression;

import exception.ParseException;
import expression.Expression;
import expression.impl.*;
import statement.Statement;
import token.Token;
import token.TokenWrapper;

import static token.TokenType.*;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class _ExpressionParser {

    public static Expression generateExpression(TokenWrapper tokens) {
        return assignment(tokens);
    }


    // chain of responsibility?
    private static Expression assignment(TokenWrapper tokens) {
        Expression expr = comparison(tokens);

        if (match(tokens, EQUALS)) {
            Token equals = tokens.getCurrentAndAdvance();
            Expression value = comparison(tokens);

            // vale la pena hacer un get de los tokens en todas las expressiones solo para evitar este instace of?
            if (expr instanceof VariableExpression) {
                Token name = ((VariableExpression)expr).getName();
                return new AssigmentExpression(name, value);
            }

            throw new ParseException("Invalid assignment target.", equals);
        }

        return expr;
    }

    // todo no tiene que estar disponible en la version 1.0
    private static Expression comparison(TokenWrapper tokens) {
        Expression expr = addition(tokens);

        if (match(tokens, GREATER, GREATEREQ, LESS, LESSEQ)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = addition(tokens);
            expr = new BinaryExpression(expr, right, operator);
        }

        return expr;
    }


    private static Expression addition(TokenWrapper tokens) {
        Expression expr = multiplication(tokens);

        if (match(tokens, MINUS, PLUS)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = multiplication(tokens);
            expr = new BinaryExpression(expr, right, operator);
        }

        return expr;
    }

    private static Expression multiplication(TokenWrapper tokens) {
        Expression expr = primary(tokens);

        if (match(tokens, DIVISION, MULTIPLICATION)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = primary(tokens);
            expr = new BinaryExpression(expr, right, operator);
        }

        return expr;
    }

    private static Expression primary(TokenWrapper tokens) {
        // todo cambiarlo por un switch
//        switch (tokens.getCurrent().getTokenType()){
//            case STRING:{
//                return new LiteralExpression(tokens.getCurrentAndAdvance().getTokenValue());
//            }
//            case NUMBER:{
//                return new LiteralExpression(Double.parseDouble(tokens.getCurrentAndAdvance().getTokenValue()));
//            }
//        }

        if (match(tokens, STRING)) {
            return new LiteralExpression(tokens.getCurrentAndAdvance().getTokenValue());
        }

        if (match(tokens, NUMBER)) {
            return new LiteralExpression(Double.parseDouble(tokens.getCurrentAndAdvance().getTokenValue()));
        }

        if (match(tokens, FALSE)) {
            tokens.advance();
            return new LiteralExpression(false);
        }
        if (match(tokens, TRUE)) {
            tokens.advance();
            return new LiteralExpression(true);
        }

        if (match(tokens, IDENTIFIER)) {
            return new VariableExpression(tokens.getCurrentAndAdvance());
        }

        if (match(tokens, LPAREN)) {
            tokens.advance();
            Expression expr = generateExpression(tokens);
            consume(tokens, RPAREN, "Expect ')' after expression.");
            return new GroupingExpression(expr);
        }

        throw new ParseException("Expect expression.", tokens.getCurrent());
    }
}
