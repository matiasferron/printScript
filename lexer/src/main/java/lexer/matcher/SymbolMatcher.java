package lexer.matcher;

import exception.LexerException;
import token.Position;
import token.Token;
import token.TokenType;

import java.util.HashMap;
import java.util.Map;

public class SymbolMatcher extends Matcher {

    private Map<String, TokenType> acceptedTokens;

    public SymbolMatcher() {
        this.acceptedTokens = new HashMap<>();
        this.acceptedTokens.put(";", TokenType.SEMICOLON );
        this.acceptedTokens.put(":", TokenType.COLON);
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
    public Token matchAndBuildToken(Position position) throws LexerException {
        return getTokenBuilder().withPosition(position).
                withType(this.acceptedTokens.get(this.getAccumulator().toString())).
                withValue(this.getAccumulator().toString())
                .build();
    }

}
