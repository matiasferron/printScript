package lexer.matcher;

import token.TokenType;

import java.util.HashMap;
import java.util.Map;

public class SymbolMatcher implements Matcher {

    private Map<String, TokenType> acceptedTokens;

    public SymbolMatcher() {
        this.acceptedTokens = new HashMap<>();
        this.acceptedTokens.put(";", TokenType.SEMICOLON );
//        this.acceptedTokens.put(TokenType.COLON, ":"); lo dejo asi para el number y string type
        this.acceptedTokens.put(".", TokenType.DOT );
        this.acceptedTokens.put( "*", TokenType.MULTIPLICATION);
        this.acceptedTokens.put("/", TokenType.DIVISION );
        this.acceptedTokens.put("(", TokenType.LPAREN );
        this.acceptedTokens.put(")", TokenType.RPAREN );
        this.acceptedTokens.put("+", TokenType.PLUS );
        this.acceptedTokens.put("-", TokenType.MINUS);
        this.acceptedTokens.put("=", TokenType.EQUALS );

    }


    @Override
    public TokenType matchAndReturnType(String toMatch) {
        return this.acceptedTokens.getOrDefault(toMatch, TokenType.UNKNOWN);
    }

    @Override
    public boolean doesMatch(String toMatch) {
        return this.acceptedTokens.containsKey(toMatch);
    }
}
