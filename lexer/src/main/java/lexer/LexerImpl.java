package lexer;

import token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

    @Override
    public List<Token> lex(Stream<Character> code) {
//        code.forEach();
        return new ArrayList<>();
    }
}
