import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import interpreter.helper.InterpreterMemory;
import lexer.Lexer;
import lexer.LexerImpl;
import parser.Parser;
import parser.ParserImpl;
import picocli.CommandLine;
import statement.Statement;
import statement.parsers.expression.*;
import statement.parsers.statment.StatementParser;
import statement.parsers.statment.impl.ExpressionStatementParser;
import statement.parsers.statment.impl.IfStatementParser;
import statement.parsers.statment.impl.VariableDeclarationParser;
import statement.parsers.statment.impl.printParser;
import token.Token;
import visitor.ExpressionVisitor;
import visitor.ExpressionVisitorHelpers.VisitBinaryHelper;
import visitor.ExpressionVisitorHelpers.VisitorExpressionHelper;
import visitor.ExpressionVisitorImpl;
import visitor.StatementVisitor;
import visitor.StatementVisitorHelpers.VisitVariableStatementHelper;
import visitor.StatementVisitorHelpers.VisitorStatementHelper;
import visitor.StatementVisitorImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class Tar implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--file"}, paramLabel = "ARCHIVE", description = "the archive file")
    private String filePath;

    @CommandLine.Option(names = {"-v", "--validate"}, description = "Activates validation only")
    private boolean onlyValidate = false;

    @CommandLine.Option(names = {"-nb", "--noBoolean"}, description = "Deactivates boolean feature")
    private boolean booleanActive = true;


    @Override
    public Integer call() {
        try {
            String sourceCode = readFile(filePath);
            Stream<Character> input = sourceCode.chars().mapToObj(intValue -> (char) intValue);

            Lexer lexer = new LexerImpl("1.1");
            List<Token> tokens = lexer.lex(input);
            Parser parser = new ParserImpl(tokens, generateIFEnvironment());
            List<Statement> statements = parser.parse();
            InterpreterMemory interpreterMemory = new InterpreterMemory();
            VisitorExpressionHelper expressionHelper = new VisitBinaryHelper();
            ExpressionVisitor expressionVisitor = new ExpressionVisitorImpl(interpreterMemory, expressionHelper);
            VisitorStatementHelper visitorStatementHelper = new VisitVariableStatementHelper();
            StatementVisitor statementVisitor =
                    new StatementVisitorImpl(expressionVisitor, interpreterMemory, visitorStatementHelper);
            Interpreter interpreter = new InterpreterImplementation(statementVisitor);

            if (onlyValidate) {
                return 0;
            }
            interpreter.interpret(statements);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
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
}
