package token;

import java.util.List;

public class TokenWrapperImp implements TokenWrapper{

    private final List<Token> tokens;
    private int position;


    public TokenWrapperImp(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    @Override
    public Token getCurrent() {
        if (!hasMoreTokens()) return tokens.get(position -1);
        return tokens.get(position);
    }

    @Override
    public Token getCurrentAndAdvance() {
        Token t = getCurrent();
        advance();
        return t;
    }

    @Override
    public Token advance() {
        position ++;
        return getCurrent();
    }

    @Override
    public boolean hasMoreTokens() {
        return position < tokens.size();
    }

    public boolean check(TokenType type) {
        if (hasMoreTokens()) return false;
        return getCurrent().getTokenType() == type;
    }
}
