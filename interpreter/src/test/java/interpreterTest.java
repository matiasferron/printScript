import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import org.junit.Test;
import parser.Parser;
import parser.ParserImpl;
import statement.Statement;
import statement.parsers.statment.StatementParser;
import statement.parsers.statment.impl.ExpressionStatementParser;
import statement.parsers.statment.impl.IfStatementParser;
import statement.parsers.statment.impl.VariableDeclarationParser;
import statement.parsers.statment.impl.printParser;
import token.Token;


import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class interpreterTest {

    Interpreter interpreter = new InterpreterImplementation();
    // ExpressionVisitor expressionVisitor = new ExpressionVisitorImpl(interpreter);
    // StatementVisitor statementVisitor = new StatementVisitorImpl(interpreter, expressionVisitor);

    static StatementParser generateEnvironment() {
        StatementParser variableDeclarationParser = new VariableDeclarationParser();
        StatementParser printParser = new printParser();
        StatementParser expressionStatementParser = new ExpressionStatementParser();

        variableDeclarationParser.setNextParser(printParser);
        printParser.setNextParser(expressionStatementParser);

        return variableDeclarationParser;
    }


    static StatementParser generateIFEnvironment() {
        StatementParser variableDeclarationParser = new VariableDeclarationParser();
        StatementParser printParser = new printParser();
        StatementParser expressionStatementParser = new ExpressionStatementParser();
        StatementParser ifStatementParser = new IfStatementParser();


        ifStatementParser.setNextParser(variableDeclarationParser);
        variableDeclarationParser.setNextParser(printParser);
        printParser.setNextParser(expressionStatementParser);

        return ifStatementParser;
    }

    static List<Token> generateStringToTokens(String message) {

        final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
        final Lexer lexer = lexerFactory.createLexer();

        Stream<Character> input = message.chars().mapToObj(intValue -> (char) intValue);

        return lexer.lex(input);
    }
    @Test
    public void test01_should_parse_declaration(){

        Parser parser = new ParserImpl(generateStringToTokens("let a: number = 2; print(a);"), generateEnvironment());

        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("2.0", interpreter.getPrintedValues().get(0));
    }


    @Test
    public void test02_should_parse_declaration_with_Arithmetic(){

        Parser parser = new ParserImpl(generateStringToTokens("let a: number = 2 + 6; print(a);"), generateEnvironment());

        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("8.0", interpreter.getPrintedValues().get(0));
    }


    @Test
    public void test03_should_parse_declaration_with_Arithmetic(){

        Parser parser = new ParserImpl(generateStringToTokens("let a: string = '5'; print(a);"), generateEnvironment());

        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("'5'", interpreter.getPrintedValues().get(0));
    }

    @Test
    public void test04_should_parse_declaration_with_Arithmetic(){

        Parser parser = new ParserImpl(generateStringToTokens("let a: number = 5; a = 7; print(a);"), generateEnvironment());

        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("7.0", interpreter.getPrintedValues().get(0));
    }

    @Test
    public void test05_should_parse_declaration_with_Boolean(){

        Parser parser = new ParserImpl(generateStringToTokens("let a: boolean = 5 > 3; print(a);"), generateEnvironment());

        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("true", interpreter.getPrintedValues().get(0));
    }

    @Test
    public void test06_should_parse_declaration_with_Boolean(){

        Parser parser = new ParserImpl(generateStringToTokens("let a: boolean = 5 < 3; print(a);"), generateEnvironment());

        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("false", interpreter.getPrintedValues().get(0));
    }

    @Test
    public void test07_parse_If_statement(){

        String toMatch = "let a = 5; if(5>3){ print(a);}else{let b = 5;};";
        Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());


        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("5.0",  interpreter.getPrintedValues().get(0));
    }

    @Test
    public void test08_parse_If_statement(){

        String toMatch = "let a = 5; let b = 4; if(5<3){ print(a);}else{ print(b);};";
        Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());


        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertEquals("4.0",  interpreter.getPrintedValues().get(0));
    }

    @Test
    public void test09_parse_If_statement(){

        String toMatch = "if(5>3){ let a = 5;};";
        Parser parser = new ParserImpl(generateStringToTokens(toMatch), generateIFEnvironment());


        List<Statement> parsedStatment = parser.parse();

        interpreter.interpret(parsedStatment);

        assertTrue(true);
    }
}
