import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import interpreter.Interpreter;
import interpreter.factory.InterpreterFactory;
import interpreter.helper.InterpreterMemory;
import java.util.List;
import java.util.stream.Stream;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import org.junit.Test;
import parser.Parser;
import parser.factory.ParserFactory;
import parser.factory.ParserFactoryImpl;
import statement.Statement;
import token.Token;

public class interpreterTest {

  ParserFactory parserFactory = ParserFactoryImpl.newParserFactory();
  private final Parser basicParser = parserFactory.createParser("1.0");
  private final Parser advanceParser = parserFactory.createParser("1.1");

  InterpreterMemory interpreterMemory = new InterpreterMemory();

  private final InterpreterFactory interpreterFactory = InterpreterFactory.newParserFactory();
  private final Interpreter interpreter =
      interpreterFactory.createInterpreter("1.1", interpreterMemory);
  private final Interpreter basicInterpreter =
      interpreterFactory.createInterpreter("1.0", interpreterMemory);

  static List<Token> generateStringToTokens(String message) {

    final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
    final Lexer lexer = lexerFactory.createLexer("1.1");

    Stream<Character> input = message.chars().mapToObj(intValue -> (char) intValue);

    return lexer.lex(input);
  }

  @Test
  public void test01_should_parse_declaration() {

    List<Statement> parsedStatements =
        basicParser.parse(generateStringToTokens("let a: number = 2; println(a);"));

    interpreter.interpret(parsedStatements);
    String expected = interpreterMemory.getPrintedValues().get(0);

    assertEquals("2", expected);
  }

  @Test
  public void test02_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements =
        basicParser.parse(generateStringToTokens("let a: number = 4 / 2; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("2", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test03_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements =
        basicParser.parse(generateStringToTokens("let a: string = '5'; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("'5'", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test04_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements =
        basicParser.parse(
            generateStringToTokens("let a: number = 5; a = 7.0; let b = 3 +a; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("7.0", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test04B_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements =
        basicParser.parse(
            generateStringToTokens("let a: number = 5; a = 7.0; let b = 3 +a; println(b);"));

    interpreter.interpret(parsedStatements);

    assertEquals("10.0", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test05_should_parse_declaration_with_Boolean() {

    List<Statement> parsedStatements =
        advanceParser.parse(
            generateStringToTokens("let a: boolean = 5 > 3; println(a); a = false; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("true", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test06_should_parse_declaration_with_Boolean() {

    List<Statement> parsedStatements =
        advanceParser.parse(generateStringToTokens("let a: boolean = 5 < 3; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("false", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test07_parse_If_statement() {

    String toMatch = "let a = 5; if(5>3){ println(a);}else{let b = 5;}";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("5", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test08_parse_If_statement() {

    String toMatch =
        "let a: number = 5; "
            + "let b = 4; "
            + "if(false){ "
            + "println(a);"
            + "a = 6;"
            + "}else{ "
            + "println(b);"
            + "}";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("4", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test08B_parse_If_statement_basic_interpreter() {

    String toMatch =
        "let a: number = 5; "
            + "let b = 4; "
            + "if(false){ "
            + "println(a);"
            + "a = 6;"
            + "}else{ "
            + "println(b);"
            + "}";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    try {
      basicInterpreter.interpret(parsedStatements);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test
  public void test08C_parse_If_statement_basic_interpreter() {

    String toMatch =
        "let a: number = 5; " + "println(a);" + "let b = 5;" + "let c = b + a;" + "println(c);";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    basicInterpreter.interpret(parsedStatements);

    assertEquals("10", interpreterMemory.getPrintedValues().get(1));
  }

  @Test
  public void test09_parse_If_statement() {

    String toMatch =
        "let z = 'hola';"
            + "if(5<3){ "
            + "const a = 5;"
            + "let b: number = 4;"
            + "println(a);"
            + "println(b);"
            + "}else{"
            + "println(z);"
            + "}";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertTrue(true);
  }

  @Test
  public void test10_resign() {

    String toMatch = "let z = 4';" + " z = 5;";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertTrue(true);
  }

  @Test
  public void test10B_notAssign() {

    String toMatch = "let z';" + " z = 5;";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertTrue(true);
  }

  @Test
  public void test011_parse_If_statement() {

    String toMatch =
        "let z = 'hola';"
            + "if(5>3){ "
            + "const a = 5;"
            + "let b: number = 4;"
            + "b = 5;"
            + "println(a);"
            + "println(b);"
            + "}else{"
            + "println(z);"
            + "}";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertTrue(true);
  }

  @Test
  public void test012_parse_If_statement() {

    String toMatch =
        "let z = 'hola';"
            + "if(5>3){ "
            + "const a = 5;"
            + "let b: number = 4;"
            + "c = 5;"
            + "println(a);"
            + "println(b);"
            + "}else{"
            + "println(z);"
            + "}";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    try {
      interpreter.interpret(parsedStatements);
    } catch (Exception e) {
      System.out.println(e);
    }

    assertTrue(true);
  }

  @Test
  public void test12_parse_string_plus_statement() {

    String toMatch = "let z:string = \"hello\";" + "let x:string = \"world\";" + "println(z+x);";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("\"hello\"" + "\"world\"", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test13_parse_minus_statement() {

    String toMatch = "let z = 5; let y = 4; println(z-y);";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("1", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test14_parse_minus_double_statement() {

    String toMatch = "let z = 5.0; let y = 4; println(z-y);";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("1.0", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test14_parse_multiply_statement() {

    String toMatch = "let z = 5.0; let y = 4; println(z*y); let w = 5; let p = 4; println(w*p); ";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("20.0", interpreterMemory.getPrintedValues().get(0));
  }
}
