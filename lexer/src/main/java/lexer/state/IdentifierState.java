package lexer.state;

import lexer.matcher.Matcher;

public class IdentifierState extends LexerState {

    public IdentifierState(Matcher matcher) {
        super(matcher);
    }
}
