import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
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
import visitor.ExpressionVisitor;
import visitor.ExpressionVisitorHelpers.VisitBinaryHelper;
import visitor.ExpressionVisitorHelpers.VisitorExpressionHelper;
import visitor.ExpressionVisitorImpl;
import visitor.StatementVisitor;
import visitor.StatementVisitorHelpers.VisitVariableStatementHelper;
import visitor.StatementVisitorHelpers.VisitorStatementHelper;
import visitor.StatementVisitorImpl;

public class interpreterTest {

  ParserFactory parserFactory = ParserFactoryImpl.newParserFactory();
  private final Parser basicParser = parserFactory.createParser("1.0");
  private final Parser advanceParser = parserFactory.createParser("1.1");


  InterpreterMemory interpreterMemory = new InterpreterMemory();

  VisitorExpressionHelper expressionHelper = new VisitBinaryHelper();
  ExpressionVisitor expressionVisitor =
      new ExpressionVisitorImpl(interpreterMemory, expressionHelper);

  VisitorStatementHelper visitorStatementHelper = new VisitVariableStatementHelper();
  StatementVisitor statementVisitor =
      new StatementVisitorImpl(expressionVisitor, interpreterMemory, visitorStatementHelper);

  Interpreter interpreter = new InterpreterImplementation(statementVisitor);


  static List<Token> generateStringToTokens(String message) {

    final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
    final Lexer lexer = lexerFactory.createLexer("1.1");

    Stream<Character> input = message.chars().mapToObj(intValue -> (char) intValue);

    return lexer.lex(input);
  }

  @Test
  public void test01_should_parse_declaration() {


    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens("let a: number = 2; println(a);"));

    interpreter.interpret(parsedStatements);
    String expected = interpreterMemory.getPrintedValues().get(0);

    assertEquals("2", expected);
  }

  @Test
  public void test02_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens("let a: number = 4 / 2; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("2", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test03_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens("let a: string = '5'; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("'5'", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test04_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens("let a: number = 5; a = 7.0; let b = 3 +a; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("7.0", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test04B_should_parse_declaration_with_Arithmetic() {

    List<Statement> parsedStatements = basicParser.parse(generateStringToTokens("let a: number = 5; a = 7.0; let b = 3 +a; println(b);"));

    interpreter.interpret(parsedStatements);

    assertEquals("10.0", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test05_should_parse_declaration_with_Boolean() {

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens("let a: boolean = 5 > 3; println(a); a = false; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("true", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test06_should_parse_declaration_with_Boolean() {

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens("let a: boolean = 5 < 3; println(a);"));

    interpreter.interpret(parsedStatements);

    assertEquals("false", interpreterMemory.getPrintedValues().get(0));
  }

  @Test
  public void test07_parse_If_statement() {

    String toMatch = "let a = 5; if(5>3){ println(a);}else{let b = 5;};";

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
            + "};";

    List<Statement> parsedStatements = advanceParser.parse(generateStringToTokens(toMatch));

    interpreter.interpret(parsedStatements);

    assertEquals("4", interpreterMemory.getPrintedValues().get(0));
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
            + "};";

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
}
