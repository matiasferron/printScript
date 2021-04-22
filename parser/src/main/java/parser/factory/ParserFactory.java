package parser.factory;

import parser.Parser;
import statement.parsers.statment.StatementParser;
import token.Token;

import java.util.List;

public interface ParserFactory {
  Parser createParser(List<Token> tokens, StatementParser statementParser);
}
