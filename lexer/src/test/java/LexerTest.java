import org.junit.Test;

import java.util.stream.Stream;

public class LexerTest {


    @Test
    public void test01_should_match_keywords(){
        Stream<Character> input = "let const number string println".chars().mapToObj(intValue -> (char) intValue);

    }
}
