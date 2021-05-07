import static org.junit.Assert.*;
import static token.TokenType.DIVISION;

import exception.ParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;
import parser.Parser;
import parser.factory.ParserFactory;
import parser.factory.ParserFactoryImpl;
import statement.Statement;
import token.*;

public class ParserTest {

  ParserFactory parserFactory = ParserFactoryImpl.newParserFactory();
  private final Parser basicParser = parserFactory.createParser("1.0");
  private final Parser advanceParser = parserFactory.createParser("1.1");

  @SneakyThrows
  public static String readFileAsString(File fileName) {
    val br = new BufferedReader(new FileReader(fileName));
    return br.lines().collect(Collectors.joining("\n"));
  }

  @SneakyThrows
  public static String getExpectedResult(String filePath) {
    File file = new File(filePath);
    return readFileAsString(file);
  }

  static List<Token> generateStringToTokens(String message) {
    final String PRINT_SCRIPT_VERSION = "1.1";

    final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
    final Lexer lexer = lexerFactory.createLexer(PRINT_SCRIPT_VERSION);

    Stream<Character> input = message.chars().mapToObj(intValue -> (char) intValue);

    return lexer.lex(input);
  }

  @Test
  public void test01_should_parse_declaration() {

    List<Statement> parsedStatement =
        basicParser.parse(generateStringToTokens("let a: number = 2; const b: string = c;"));

    final String expected = getExpectedResult("./src/test/java/resources/parse_declaration.txt");

    assertEquals(expected, parsedStatement.toString());
  }

  @Test
  public void test02_should_parse_resign() {

    String toMatch = "let a: number = 2; a = 6;";

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens(toMatch));

    final String expected = getExpectedResult("./src/test/java/resources/parse_resign.txt");

    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test03_should_parse_string_declaration_and_println() {

    String toMatch = "const b:string = '6'; println(6 + 6);";

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens(toMatch));

    final String expected =
        getExpectedResult("./src/test/java/resources/string_declaration_and_println.txt");

    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test04_should_parse_string_declaration() {

    String toMatch = "const b:string = 6; const c:string = c; const d=b+c;";

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens(toMatch));

    System.out.println(parsedStatements.toString());

    final String expected = getExpectedResult("./src/test/java/resources/string_declaration.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test05_should_parse_multiplication() {

    String toMatch = "const b:number = 6*(9+7);";

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens(toMatch));

    final String expected = getExpectedResult("./src/test/java/resources/parse_multiplication.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test06_should_parse_Division() {

    String toMatch = "const b:number = 6/(9/7);";

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens(toMatch));

    final String expected = getExpectedResult("./src/test/java/resources/division.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test06_fail_parse_statement_without_SEMICOLON() {

    String toMatch = "const b:number = 6/(9/7)";

    Token token = new TokenFactory().create(DIVISION, "", new Position(0, 20));
    try {
      basicParser.parse(generateStringToTokens(toMatch));
    } catch (Exception e) {
      assertEquals(
          new ParseException("Expect ';' after variable declaration.", token).getMessage(),
          e.getMessage());
    }
  }

  @Test
  public void test06_fail_parse_statement_without_type_Declaration() {

    String toMatch = "const b: = 6";

    Token token = new TokenFactory().create(DIVISION, "", new Position(0, 7));
    try {
      basicParser.parse(generateStringToTokens(toMatch));
    } catch (Exception e) {
      assertEquals(
          new ParseException("Need to specify variable type", token).getMessage(), e.getMessage());
    }
  }

  @Test
  public void test06_parse_multiple_statement() {

    String toMatch = "const a:number = 6;" + "b = 7;" + "println(b);";

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens(toMatch));

    final String expected =
        getExpectedResult("./src/test/java/resources/parse_multiple_statement.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test06_parse_If_statement() {

    String toMatch = "if(true){ let a = 5; const b: number = 5}";

    Token token = new TokenFactory().create(DIVISION, "", new Position(0, 31));

    try {
      advanceParser.parse(generateStringToTokens(toMatch));
    } catch (Exception e) {
      assertEquals(
          new ParseException("Expect ';' after variable declaration.", token).getMessage(),
          e.getMessage());
    }
  }

  @Test
  public void test07_parse_boolean_statement() {

    String toMatch = "let a:boolean = true;";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    final String expected = getExpectedResult("./src/test/java/resources/boolean_statement.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test08_parse_boolean_condition_statement() {

    String toMatch = "let a:boolean = 5>3;";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    final String expected =
        getExpectedResult("./src/test/java/resources/boolean_condition_statement.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test09_parse_boolean_condition_statement() {

    String toMatch = "let a = 5<3;";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    final String expected = getExpectedResult("./src/test/java/resources/boolean_condition.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test09_parse_boolean_GreaterEquals_condition_statement() {

    String toMatch = "let a:boolean = (5>=3);";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    final String expected =
        getExpectedResult("./src/test/java/resources/greater_equals_condition.txt");
    assertEquals(expected, parsedStatements.toString());
  }

  @Test
  public void test09_parse_Combine_boolean_condition_statement() {

    String toMatch = "let a = 5>(3+3);";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    final String expected =
        getExpectedResult("./src/test/java/resources/combine_boolean_condition.txt");
    assertEquals(expected, parsedStatements.toString());
  }
}
