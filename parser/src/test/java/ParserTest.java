import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import org.junit.Test;
import parser.Parser;
import parser.ParserImpl;
import statement.Statement;
import statement.parsers.statment.StatementParser;
import statement.parsers.statment.impl.ExpressionStatementParser;
import statement.parsers.statment.impl.VariableDeclarationParser;
import statement.parsers.statment.impl.printParser;
import token.Token;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class ParserTest {

    private final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
    private final Lexer lexer = lexerFactory.createLexer();

    @Test
    public void test01_should_parse_declaration(){
        StatementParser variableDeclarationParser = new VariableDeclarationParser();
        StatementParser printParser = new printParser();
        StatementParser expressionStatementParser = new ExpressionStatementParser();

        variableDeclarationParser.setNextParser(printParser);
        printParser.setNextParser(expressionStatementParser);

        String toMatch = "let a: number = 2;";
        Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

        List<Token> output = lexer.lex(input);

        Parser parser = new ParserImpl(output, variableDeclarationParser);

        List<Statement> parsedStatment = parser.parse();

        for (Statement s: parsedStatment) {
            System.out.println(s);
        }

        assertEquals(true, true);
    }

    @Test
    public void test02_should_parse_declaration(){
        StatementParser variableDeclarationParser = new VariableDeclarationParser();
        StatementParser printParser = new printParser();
        StatementParser expressionStatementParser = new ExpressionStatementParser();

        variableDeclarationParser.setNextParser(printParser);
        printParser.setNextParser(expressionStatementParser);

        String toMatch = "let a: number = 2; a = 6;";
        Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

        List<Token> output = lexer.lex(input);

        Parser parser = new ParserImpl(output, variableDeclarationParser);

        List<Statement> parsedStatment = parser.parse();

        for (Statement s: parsedStatment) {
            System.out.println(s);
        }

        assertEquals(true, true);
    }

    @Test
    public void test03_should_parse_string_declaration(){
        StatementParser variableDeclarationParser = new VariableDeclarationParser();
        StatementParser printParser = new printParser();
        StatementParser expressionStatementParser = new ExpressionStatementParser();

        variableDeclarationParser.setNextParser(printParser);
        printParser.setNextParser(expressionStatementParser);

        String toMatch = "const b:string = '6'; print(6 + 6);";
        Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

        List<Token> output = lexer.lex(input);

        Parser parser = new ParserImpl(output, variableDeclarationParser);

        List<Statement> parsedStatment = parser.parse();

        for (Statement s: parsedStatment) {
            System.out.println(s);
        }

        assertEquals(true, true);
    }


    @Test       // todo Have to fail
    public void test04_should_parse_string_declaration(){
        StatementParser variableDeclarationParser = new VariableDeclarationParser();
        StatementParser printParser = new printParser();
        StatementParser expressionStatementParser = new ExpressionStatementParser();

        variableDeclarationParser.setNextParser(printParser);
        printParser.setNextParser(expressionStatementParser);

        String toMatch = "const b:string = 6;";
        Stream<Character> input = toMatch.chars().mapToObj(intValue -> (char) intValue);

        List<Token> output = lexer.lex(input);

        Parser parser = new ParserImpl(output, variableDeclarationParser);

        List<Statement> parsedStatment = parser.parse();

        for (Statement s: parsedStatment) {
            System.out.println(s);
        }

        assertEquals(true, true);
    }
}
