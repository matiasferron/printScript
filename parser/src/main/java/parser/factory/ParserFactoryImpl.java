package parser.factory;

import java.util.List;
import parser.Parser;
import parser.ParserImpl;
import statement.parsers.statment.StatementParser;
import token.Token;

public class ParserFactoryImpl implements ParserFactory {

  public static ParserFactory newParserFactory() {
    return new ParserFactoryImpl();
  }

  @Override
  public Parser createParser(List<Token> tokens, StatementParser statementParser) {
    return new ParserImpl(tokens, statementParser);
  }
}
