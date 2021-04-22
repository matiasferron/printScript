package lexer.factory;

import lexer.Lexer;
import lexer.LexerImpl;

public class LexerFactoryImpl implements LexerFactory {

  public static LexerFactory newLexerFactory() {
    return new LexerFactoryImpl();
  }

  @Override
  public Lexer createLexer(String printScriptVersion) {
    return new LexerImpl(printScriptVersion);
  }
}
