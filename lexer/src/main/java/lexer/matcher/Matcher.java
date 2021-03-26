package lexer.matcher;

import token.TokenType;

public interface Matcher {

    TokenType matchAndReturnType(String toMatch);
    boolean doesMatch(String toMatch);
}
