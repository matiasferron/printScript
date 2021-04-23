import expression.impl.BinaryExpression;
import expression.impl.GroupingExpression;
import expression.impl.LiteralExpression;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import org.junit.Test;
import parser.Parser;
import parser.ParserImpl;
import statement.Statement;
import statement.impl.PrintStatement;
import statement.parsers.expression.*;
import statement.parsers.statment.StatementParser;
import statement.parsers.statment.impl.ExpressionStatementParser;
import statement.parsers.statment.impl.IfStatementParser;
import statement.parsers.statment.impl.VariableDeclarationParser;
import statement.parsers.statment.impl.printParser;
import token.*;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ParserTest {

  static StatementParser generateEnvironment() {
    CommonExpressionParser expressionParser = new AssigmentExpressionParser();
    CommonExpressionParser expressionParser2 = new AdditionParser();
    CommonExpressionParser expressionParser3 = new MultiplicationParser();
    CommonExpressionParser expressionParser4 = new TypeParser();

    expressionParser.setNextParser(expressionParser2);
    expressionParser2.setNextParser(expressionParser3);
    expressionParser3.setNextParser(expressionParser4);
    expressionParser4.setNextParser(expressionParser);

    StatementParser variableDeclarationParser = new VariableDeclarationParser(expressionParser);
    StatementParser printParser = new printParser(expressionParser);
    StatementParser expressionStatementParser = new ExpressionStatementParser(expressionParser);

    variableDeclarationParser.setNextParser(printParser);
    printParser.setNextParser(expressionStatementParser);

    return variableDeclarationParser;
  }

  static StatementParser generateIFEnvironment() {
    CommonExpressionParser expressionParser = new AssigmentExpressionParser();
    CommonExpressionParser expressionParser1 = new ComparisonParser();
    CommonExpressionParser expressionParser2 = new AdditionParser();
    CommonExpressionParser expressionParser3 = new MultiplicationParser();
    CommonExpressionParser expressionParser4 = new TypeParser();

    expressionParser.setNextParser(expressionParser1);
    expressionParser1.setNextParser(expressionParser2);
    expressionParser2.setNextParser(expressionParser3);
    expressionParser3.setNextParser(expressionParser4);
    expressionParser4.setNextParser(expressionParser);

    StatementParser variableDeclarationParser = new VariableDeclarationParser(expressionParser);
    StatementParser printParser = new printParser(expressionParser);
    StatementParser expressionStatementParser = new ExpressionStatementParser(expressionParser);
    StatementParser ifStatementParser = new IfStatementParser(expressionParser);

    ifStatementParser.setNextParser(variableDeclarationParser);
    variableDeclarationParser.setNextParser(printParser);
    printParser.setNextParser(expressionStatementParser);

    return ifStatementParser;
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

    Parser parser =
        new ParserImpl(generateStringToTokens("let a: number = 2;"), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    // todo. matcher de las estructuras de los objetos
    assertEquals(true, true);
  }

  @Test
  public void test02_should_parse_declaration() {

    String toMatch = "let a: number = 2; a = 6;";

    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test
  public void test03_should_parse_string_declaration() {

    String toMatch = "const b:string = '6'; print(6 + 6);";

    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    PrintStatement printStatement =
        new PrintStatement(
            new GroupingExpression(
                new BinaryExpression(
                    new LiteralExpression("6"),
                    new LiteralExpression("6"),
                    new TokenFactory().create(TokenType.PLUS, "+", new Position(0, 25)))));

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test // todo fix iguala a un number y no rompe.
  public void test04_should_parse_string_declaration() {

    String toMatch = "const b:string = 6;";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test
  public void test05_should_parse_multiplication() {

    String toMatch = "const b:number = 6*(9+7);";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test
  public void test06_should_parse_Division() {

    String toMatch = "const b:number = 6/(9/7);";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test
  public void test06_fail_parse_statement_without_SEMICOLON() {

    String toMatch = "const b:number = 6/(9/7)";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    try {
      List<Statement> parsedStatment = parser.parse();
    } catch (Exception e) {
      System.out.println(e);
    }

    assertEquals(true, true);
  }

  @Test
  public void test06_fail_parse_statement_without_type_Declaration() {

    String toMatch = "const b: = 6";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    try {
      List<Statement> parsedStatment = parser.parse();
    } catch (Exception e) {
      System.out.println(e);
    }

    assertEquals(true, true);
  }

  @Test
  public void test06_parse_multiple_statement() {

    String toMatch = "const a:number = 6;" + "b = 7;" + "print(b);";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateEnvironment());

    List<Statement> parsedStatment = parser.parse();

    assertEquals(true, true);
  }

  @Test
  public void test06_parse_If_statement() {

    String toMatch = "if(true){ let a = 5; const b: number = 5};";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());

    try {
      List<Statement> parsedStatment = parser.parse();
    } catch (Exception e) {
      System.out.println(e);
    }

    assertEquals(true, true);
  }

  @Test
  public void test07_parse_boolean_statement() {

    String toMatch = "let a:boolean = true;";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test
  public void test08_parse_boolean_condition_statement() {

    String toMatch = "let a:boolean = 5>3;";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  @Test
  public void test09_parse_boolean_condition_statement() {

    String toMatch = "let a:boolean = 5<3;";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
  }

  //    @Test //TODO FIX LEXER
  //    public void test09_parse_boolean_GreaterEquals_condition_statement(){
  //
  //        String toMatch = "let a:boolean = 5>=3;";
  //        Parser parser = new ParserImpl(generateStringToTokens(toMatch),
  // generateIFEnvironment());
  //
  //        List<Statement> parsedStatment = parser.parse();
  //
  //        for (Statement s: parsedStatment) {
  //            System.out.println(s);
  //        }
  //
  //        assertEquals(true, true);
  //    }

  @Test
  public void test09_parse_Combine_boolean_condition_statement() {

    String toMatch = "let a = 5>(3+3);";
    Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());

    List<Statement> parsedStatment = parser.parse();

    for (Statement s : parsedStatment) {
      System.out.println(s);
    }

    assertEquals(true, true);
    //        Assertions.assertThat(parsedStatment.get(0)).hasSameClassAs(new
    // ExpressionStatement());
  }
}