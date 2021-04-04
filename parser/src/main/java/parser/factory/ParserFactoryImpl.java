package parser.factory;

import parser.Parser;
import parser.ParserImpl;
import token.Token;

import java.util.List;

public class ParserFactoryImpl implements ParserFactory {

    public static ParserFactory newParserFactory() { return new ParserFactoryImpl(); }

    @Override
    public Parser createParser(List<Token> tokens) {
        return new ParserImpl(tokens);
    }
}
