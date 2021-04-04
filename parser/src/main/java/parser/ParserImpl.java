package parser;

import exception.ParseException;
import expression.Expression;
import expression.impl.*;
import statement.Statement;
import statement.impl.ExpressionStatement;
import statement.impl.PrintStatement;
import statement.impl.VariableStatement;
import token.Token;
import token.TokenType;
import token.TokenWrapperImp;

import java.util.ArrayList;
import java.util.List;

import static token.TokenType.*;

public class ParserImpl implements Parser {
    private final TokenWrapperImp tokens;

    public ParserImpl(List<Token> tokens) {
        this.tokens = new TokenWrapperImp(tokens);
    }

    @Override
    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        while (tokens.hasMoreTokens()) {
            statements.add(init());
        }

        return statements;
    }

    private Statement init() {
        if (match(LET, CONST)) return variableStatement(tokens.getCurrent());
        if (match(PRINTLN))  return printStatement();
        return expressionStatement();
    }

    // todo cada uno en su clase

    private Statement expressionStatement() {
        Expression expr = generateExpression();
        consume(SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStatement(expr);
    }



    // Possibles statements
    private Statement variableStatement(Token keyword) {
        Token name = consume(IDENTIFIER, "Expect variable name.");

        TokenType type = null;
        Expression initializer = null;

        if (match(COLON)){
           type = checkTypeAssignation();
        }
        if (match(EQUALS)) {
            initializer = generateExpression();
        }

        consume(SEMICOLON, "Expect ';' after variable declaration.");
        return new VariableStatement(name, initializer, type, keyword);
    }

    private TokenType checkTypeAssignation(){
        switch (tokens.getCurrent().getTokenType()){
            case NUMBER: {
                return NUMBER;
            }
            case STRING: {
                return STRING;
            }
            default: {
                throw new ParseException("Need to specify variable type", tokens.getCurrent());
            }
        }
    }

    private Statement printStatement() {
        Expression value = generateExpression();
        consume(SEMICOLON, "Expect ';' after value.");
        return new PrintStatement(value);
    }


    //
    // Generate expression process
    //
    private Expression generateExpression() {
        return assignment();
    }


    // chain of responsibility?
    private Expression assignment() {
        Expression expr = addition();

        if (match(EQUALS)) {
            Token equals = tokens.getCurrentAndAdvance();
            Expression value = assignment();

            // vale la pena hacer un get de los tokens en todas las expressiones solo para evitar este instace of?
            if (expr instanceof VariableExpression) {
                Token name = ((VariableExpression)expr).getName();
                return new AssigmentExpression(name, value);
            }

            throw new ParseException("Invalid assignment target.", equals);
        }

        return expr;
    }


    private Expression addition() {
        Expression expr = multiplication();

        if (match(MINUS, PLUS)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = multiplication();
            expr = new BinaryExpression(expr, right, operator);
        }

        return expr;
    }

    private Expression multiplication() {
        Expression expr = unary();

        if (match(DIVISION, MULTIPLICATION)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = unary();
            expr = new BinaryExpression(expr, right, operator);
        }

        return expr;
    }

    private Expression unary() {
        if (match(MINUS)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = unary();
            return new UnaryExpression(operator, right);
        }

        return primary();
    }

    private Expression primary() {

        if (match(NUMBER, STRING)) {
            return new LiteralExpression(tokens.getCurrentAndAdvance().getValue());
        }

        if (match(IDENTIFIER)) {
            return new VariableExpression(tokens.getCurrentAndAdvance());
        }

        if (match(LPAREN)) {
            Expression expr = generateExpression();
            consume(RPAREN, "Expect ')' after expression.");
            return new GroupingExpression(expr);
        }

        throw new ParseException("Expect expression.", tokens.getCurrent());
    }

    // aux method

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (tokens.check(type)) {
                //tokens.advance();
                return true;
            }
        }

        return false;
    }


    private Token consume(TokenType type, String message) {
        if (tokens.check(type)) return tokens.advance();
        throw new ParseException(message, tokens.getCurrent());
    }

}
