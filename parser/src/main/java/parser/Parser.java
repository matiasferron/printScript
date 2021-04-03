package parser;

import token.Token;

import java.util.List;

public interface Parser {
    public void parse(List<Token> tokens);
}
