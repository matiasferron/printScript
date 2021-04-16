package statement.parsers.statment.impl;

import exception.ParseException;
import expression.Expression;
import statement.Statement;
import statement.impl.VariableStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.expression._ExpressionParser;
import statement.parsers.statment.StatementParser;
import token.Token;
import token.TokenType;
import token.TokenWrapper;

import static token.TokenType.*;
import static token.TokenType.SEMICOLON;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class VariableDeclarationParser extends StatementParser {


    // Todo pasarle una lista con los expression parser que podria parsear este statment. Asi cada statment tiene sus pisibles expresiones parseables??
    public VariableDeclarationParser() {
        super();
    }

    @Override
    public Statement parse(TokenWrapper tokens) {
        Token keyword = tokens.getCurrent();
        if (match(tokens, LET, CONST)) {
            tokens.advance();
            Token name = consume(tokens, IDENTIFIER, "Expect variable name.");

            TokenType type = null;
            Expression initializer = null;

            if (match(tokens, COLON)){
                tokens.advance();
                type = checkTypeAssignation(tokens);
                tokens.advance();
            }
            if (match(tokens, EQUALS)) {
                tokens.advance();
                initializer = _ExpressionParser.generateExpression(tokens);
            }

            consume(tokens, SEMICOLON, "Expect ';' after variable declaration.");
            return new VariableStatement(name, initializer, type, keyword);
        }else {
            // todo revisar. Ver que hacer en una situacion que no se le asigno un next parser y no matcheo en la primea condicion
            if (nextParser == null) throw new ParseException("Parsing error", tokens.getCurrent());
            return nextParser.parse(tokens);
        }

    }


    private TokenType checkTypeAssignation(TokenWrapper tokens){
        switch (tokens.getCurrent().getTokenType()){
            case NUMBERTYPE: {
                return NUMBERTYPE;
            }
            case STRINGTYPE: {
                return STRINGTYPE;
            }
            default: {
                throw new ParseException("Need to specify variable type", tokens.getCurrent());
            }
        }
    }
}
