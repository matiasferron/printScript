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
import parser.ParserImpl;
import statement.Statement;
import statement.parsers.expression.*;
import statement.parsers.statment.StatementParser;
import statement.parsers.statment.impl.ExpressionStatementParser;
import statement.parsers.statment.impl.IfStatementParser;
import statement.parsers.statment.impl.VariableDeclarationParser;
import statement.parsers.statment.impl.printParser;
import token.*;

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

        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatement = parser.parse(generateStringToTokens("let a: number = 2; const b: string = c;"));

        final String expected = getExpectedResult("./src/test/java/resources/parse_declaration.txt");

        assertEquals(expected, parsedStatement.toString());
    }

    @Test
    public void test02_should_parse_resign() {

        String toMatch = "let a: number = 2; a = 6;";

        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/parse_resign.txt");

        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test03_should_parse_string_declaration_and_println() {

        String toMatch = "const b:string = '6'; println(6 + 6);";

        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/string_declaration_and_println.txt");


        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test04_should_parse_string_declaration() {

        String toMatch = "const b:string = 6; const c:string = c; const d=b+c;";
        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        System.out.println(parsedStatements.toString());

        final String expected = getExpectedResult("./src/test/java/resources/string_declaration.txt");
        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test05_should_parse_multiplication() {

        String toMatch = "const b:number = 6*(9+7);";
        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/parse_multiplication.txt");
        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test06_should_parse_Division() {

        String toMatch = "const b:number = 6/(9/7);";
        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));


        final String expected = getExpectedResult("./src/test/java/resources/division.txt");
        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test06_fail_parse_statement_without_SEMICOLON() {

        String toMatch = "const b:number = 6/(9/7)";
        Parser parser = new ParserImpl(generateEnvironment());

        Token token = new TokenFactory().create(DIVISION, "", new Position(0, 20));
        try {
            parser.parse(generateStringToTokens(toMatch));
        } catch (Exception e) {
            assertEquals(new ParseException("Expect ';' after variable declaration.", token).getMessage(), e.getMessage());
        }


    }

    @Test
    public void test06_fail_parse_statement_without_type_Declaration() {

        String toMatch = "const b: = 6";
        Parser parser = new ParserImpl(generateEnvironment());

        Token token = new TokenFactory().create(DIVISION, "", new Position(0, 7));
        try {
            parser.parse(generateStringToTokens(toMatch));
        } catch (Exception e) {
            assertEquals(new ParseException("Need to specify variable type", token).getMessage(), e.getMessage());
        }

    }

    @Test
    public void test06_parse_multiple_statement() {

        String toMatch = "const a:number = 6;" + "b = 7;" + "println(b);";
        Parser parser = new ParserImpl(generateEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/parse_multiple_statement.txt");
        assertEquals(expected, parsedStatements.toString());

    }

    @Test
    public void test06_parse_If_statement() {

        String toMatch = "if(true){ let a = 5; const b: number = 5};";
        Parser parser = new ParserImpl(generateIFEnvironment());

        Token token = new TokenFactory().create(DIVISION, "", new Position(0, 31));

        try {
            parser.parse(generateStringToTokens(toMatch));
        } catch (Exception e) {
            assertEquals(new ParseException("Expect ';' after variable declaration.", token).getMessage(), e.getMessage());
        }
    }

    @Test
    public void test07_parse_boolean_statement() {

        String toMatch = "let a:boolean = true;";
        Parser parser = new ParserImpl(generateIFEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/boolean_statement.txt");
        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test08_parse_boolean_condition_statement() {

        String toMatch = "let a:boolean = 5>3;";
        Parser parser = new ParserImpl(generateIFEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/boolean_condition_statement.txt");
        assertEquals(expected, parsedStatements.toString());
    }

    @Test
    public void test09_parse_boolean_condition_statement() {

        String toMatch = "let a = 5<3;";
        Parser parser = new ParserImpl(generateIFEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/boolean_condition.txt");
        assertEquals(expected, parsedStatements.toString());
    }

//        @Test //TODO FIX LEXER
//        public void test09_parse_boolean_GreaterEquals_condition_statement(){
//
//            String toMatch = "let a:boolean = (5>=3);";
//            Parser parser = new ParserImpl(
//                 generateIFEnvironment());
//
//            List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));
//
//            System.out.println(parsedStatements.toString());
//
//            assertEquals(true, true);
//        }

    @Test
    public void test09_parse_Combine_boolean_condition_statement() {

        String toMatch = "let a = 5>(3+3);";
        Parser parser = new ParserImpl(generateIFEnvironment());

        List<Statement> parsedStatements = parser.parse(generateStringToTokens(toMatch));

        final String expected = getExpectedResult("./src/test/java/resources/combine_boolean_condition.txt");
        assertEquals(expected, parsedStatements.toString());

    }
}
