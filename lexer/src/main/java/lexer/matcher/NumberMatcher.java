package lexer.matcher;

import token.TokenType;

public class NumberMatcher implements Matcher {


    @Override
    public TokenType matchAndReturnType(String toMatch) {
        return null;
    }

    @Override
    public boolean doesMatch(String toMatch) {
        return false;
    }
}
