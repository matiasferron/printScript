package parser.factory;

import java.util.List;
import parser.Parser;
import statement.parsers.statment.StatementParser;
import token.Token;

public interface ParserFactory {
  Parser createParser(List<Token> tokens, StatementParser statementParser);
}
