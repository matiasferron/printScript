package lexer.factory;

import lexer.Lexer;
import lexer.LexerImpl;
import lexer.factory.LexerFactory;

public class LexerFactoryImpl implements LexerFactory {

    public static LexerFactory newLexerFactory() {
        return new LexerFactoryImpl();
    }

    @Override
    public Lexer createLexer() {
        return new LexerImpl();
    }
}
