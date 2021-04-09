package lexer.matcher;

import exception.LexerException;
import token.Position;
import token.Token;
import token.builder.TokenBuilder;
import token.builder.TokenBuilderImpl;

public abstract class Matcher {

    private final TokenBuilder tokenBuilder;
    private StringBuilder accumulator;

    public Matcher() {
        this.tokenBuilder = TokenBuilderImpl.createBuilder();
        this.accumulator = new StringBuilder();
    }

    public abstract Token matchAndBuildToken(Position position) throws LexerException;

    public boolean isNumber(Character character) {
        return Character.isDigit(character);
    }

    public boolean isAlpha(Character character) {
        return Character.isAlphabetic(character);
    }

    public TokenBuilder getTokenBuilder() {
        return tokenBuilder;
    }

    public StringBuilder getAccumulator() {
        return accumulator;
    }
}
