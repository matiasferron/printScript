import exception.UnrecognizedTokenException;
import org.junit.Test;

import java.util.stream.Stream;

public class LexerTest {


    @Test
    public void test01_should_match_symbols(){
        Stream<Character> input = "(( () / * + = - : ; .".chars().mapToObj(intValue -> (char) intValue);

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
    public void test04_should_return_lexical_error()
//            throws UnrecognizedTokenException
    {
        Stream<Character> input = "let a: number = 2;".chars().mapToObj(intValue -> (char) intValue);
//        throw new UnrecognizedTokenException();

    }
}
