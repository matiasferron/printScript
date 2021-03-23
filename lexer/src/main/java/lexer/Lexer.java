package lexer;

import token.Token;

import java.util.List;
import java.util.stream.Stream;

public interface Lexer {
    List<Token> lex(Stream<Character> code);
}
