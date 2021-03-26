package lexer.state;

import lexer.matcher.KeyWordMatcher;
import lexer.matcher.Matcher;

public class KeyWordState extends LexerState {

    public KeyWordState(Matcher matcher) {
        super(matcher);
    }
}
