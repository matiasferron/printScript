package parser.factory;

import parser.Parser;
import token.Token;

import java.util.List;

public interface ParserFactory {
    Parser createParser(List<Token> tokens);
}
