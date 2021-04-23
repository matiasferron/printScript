import static org.junit.Assert.assertEquals;

import exception.UnrecognizedTokenException;
import java.util.List;
import java.util.stream.Stream;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import org.junit.Test;
import token.Token;
import token.TokenType;

public class LexerTest {
  private final String PRINT_SCRIPT_VERSION = "1.1";
  private final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
  private final Lexer lexer = lexerFactory.createLexer(PRINT_SCRIPT_VERSION);

  @Test
  public void test01_should_match_symbols() {
    String toMatch = "(( () / * + = - : ; .";
    Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);
    List<Token> output = lexer.lex(input);

    StringBuilder expected = new StringBuilder();
    for (Token t : output) {
      expected.append(t.getTokenValue());
    }

    assertEquals(toMatch.replace(" ", ""), expected.toString());
  }

  @Test
  public void test02_should_match_keywords() {
    String toMatch = "let const number string println";
    Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);
    List<Token> output = lexer.lex(input);

    StringBuilder expected = new StringBuilder();
    for (Token t : output) {
      expected.append(t.getTokenValue());
    }
    assertEquals(toMatch.replace(" ", ""), expected.toString());
  }

  @Test
  public void test03_should_match_declaration() {
    String toMatch = "let a: number = 2;";
    Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

    List<Token> output = lexer.lex(input);

    StringBuilder expected = new StringBuilder();
    for (Token t : output) {
      expected.append(t.getTokenValue());
    }
    assertEquals(toMatch.replace(" ", ""), expected.toString());
  }

  @Test
  public void test04_should_match_string() {
    String toMatch = "\"asd\"";
    Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);
    List<Token> output = lexer.lex(input);

    assertEquals(output.get(0).getTokenType(), TokenType.STRING);
  }

  @Test
  public void test05_should_return_lexical_error() throws UnrecognizedTokenException {
    Stream<Character> input = "#".chars().mapToObj(intValue -> (char) intValue);
    //        throw new UnrecognizedTokenException("Unrecognized token found at
    // {{token.position}}",token);
  }

  @Test
  public void test06_different_cases_test() {
    String toMatch = "let a: number; a = 2; a = a + 3; println(a); let b: string = \"asd\";";
    Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

    List<Token> output = lexer.lex(input);

    StringBuilder expected = new StringBuilder();
    for (Token t : output) {
      expected.append(t.getTokenValue());
    }
    assertEquals(toMatch.replace(" ", ""), expected.toString());
  }

  @Test
  public void test07_booleans_test() {
    String toMatch = "if( 2 != 3) { } else {}";
    Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

    List<Token> output = lexer.lex(input);

    StringBuilder expected = new StringBuilder();
    for (Token t : output) {
      expected.append(t.getTokenValue());
    }

    assertEquals(toMatch.replace(" ", ""), expected.toString());
  }
}
