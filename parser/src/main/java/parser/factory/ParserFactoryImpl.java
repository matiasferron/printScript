package parser.factory;

import parser.Parser;
import parser.ParserImpl;
import statement.parsers.statment.StatementParser;
import token.Token;

import java.util.List;

public class ParserFactoryImpl implements ParserFactory {

  public static ParserFactory newParserFactory() {
    return new ParserFactoryImpl();
  }

  @Override
  public Parser createParser(List<Token> tokens, StatementParser statementParser) {
    return new ParserImpl(tokens, statementParser);
  }
}
