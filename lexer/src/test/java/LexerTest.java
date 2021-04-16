import exception.UnrecognizedTokenException;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import org.junit.Test;
import token.Token;

import java.util.List;
import java.util.stream.Stream;


public class LexerTest {

    private final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
    private final Lexer lexer = lexerFactory.createLexer();

    @Test
    public void test01_should_match_symbols(){
        String s = "(( () / * + = - : ; .";
        Stream<Character> input = s.chars().mapToObj(intValue -> (char) intValue);
        List<Token> output = lexer.lex(input);
        for (Token t:output) {
            System.out.println(t.getTokenValue());
        }
    }

    @Test
    public void test02_should_match_keywords(){
        Stream<Character> input = "let const number string println".chars().mapToObj(intValue -> (char) intValue);

    }

    @Test
    public void test03_should_match_declaration(){
        Stream<Character> input = "let a: number = 2;".chars().mapToObj(intValue -> (char) intValue);

    }

    @Test
    public void test04_should_match_string() {
        Stream<Character> input = "#^@".chars().mapToObj(intValue -> (char) intValue);

    }

    @Test
    public void test05_should_return_lexical_error() throws UnrecognizedTokenException {
        Stream<Character> input = "#".chars().mapToObj(intValue -> (char) intValue);
//        throw new UnrecognizedTokenException("Unrecognized token found at {{token.position}}",token);

    }

    @Test
    public void test06_integration_test() {
        Stream<Character> input = "let a: number; a = 2; a = a + 3; println(a); let b: string = 'asd';".chars().mapToObj(intValue -> (char) intValue);

    }

}
