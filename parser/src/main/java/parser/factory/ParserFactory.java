package parser.factory;

import parser.Parser;

public interface ParserFactory {
  Parser createParser(String version);
}
