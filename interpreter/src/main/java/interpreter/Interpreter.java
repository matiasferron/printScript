package interpreter;


import statement.Statement;
import token.Token;
import token.TokenType;

import java.util.List;


public interface Interpreter{
    void interpret(List<Statement> statements);
}
