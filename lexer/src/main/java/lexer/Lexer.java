package lexer;

import java.util.List;
import java.util.stream.Stream;
import token.Token;

public interface Lexer {

  List<Token> lex(Stream<Character> stream);
}
