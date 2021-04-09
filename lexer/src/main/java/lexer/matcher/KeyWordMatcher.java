package lexer.matcher;

import exception.LexerException;
import token.Position;
import token.Token;
import token.TokenType;

import java.util.HashMap;
import java.util.Map;

public class KeyWordMatcher extends Matcher {

    private Map<String, TokenType> acceptedTokens;

    public KeyWordMatcher() {
        this.acceptedTokens = new HashMap<>();
        this.acceptedTokens.put("const", TokenType.CONST );
        this.acceptedTokens.put("let", TokenType.LET);
        this.acceptedTokens.put("println", TokenType.PRINTLN );
        this.acceptedTokens.put( "number", TokenType.NUMBER_TYPE);
        this.acceptedTokens.put("string", TokenType.STRING_TYPE );
    }

    @Override
    public Token matchAndBuildToken(Position position) throws LexerException {
        return getTokenBuilder().withPosition(position).
                withType(this.acceptedTokens.get(this.getAccumulator().toString())).
                withValue(this.getAccumulator().toString())
                .build();
    }
}
