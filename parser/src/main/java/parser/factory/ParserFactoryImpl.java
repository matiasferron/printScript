package parser.factory;

import parser.Parser;
import parser.ParserImpl;

public class ParserFactoryImpl implements ParserFactory {

    public static ParserFactory newParserFactory() { return new ParserFactoryImpl(); }

    @Override
    public Parser createParser() {
        return new ParserImpl();
    }
}
