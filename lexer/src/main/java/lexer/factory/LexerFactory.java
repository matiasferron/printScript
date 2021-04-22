package lexer.factory;

import lexer.Lexer;

public interface LexerFactory {
    Lexer createLexer(String printScriptVersion);
}
