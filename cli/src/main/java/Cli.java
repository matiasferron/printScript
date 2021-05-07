import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import interpreter.helper.InterpreterMemory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import parser.Parser;
import parser.factory.ParserFactoryImpl;
import picocli.CommandLine;
import statement.Statement;
import token.Token;
import visitor.ExpressionVisitor;
import visitor.ExpressionVisitorImpl;
import visitor.ExpressionVisitorResolvers.BinaryExpressionResolver;
import visitor.ExpressionVisitorResolvers.BinaryResolverComparisonImpl;
import visitor.StatementVisitor;
import visitor.StatementVisitorImpl;
import visitor.StatementVisitorResolvers.VariableStatementResolver;
import visitor.StatementVisitorResolvers.VariableStatementResolverBooleanImpl;

public class Cli implements Callable<Integer> {

  @CommandLine.Option(
      names = {"-f", "--file"},
      description = "File path",
      required = true)
  String filePath;

  @CommandLine.Option(
      names = {"-m", "--mode"},
      description = "Execution mode. Interpretation for full execution, Parsing for just parsing",
      required = true)
  String executionMode;

  @CommandLine.Option(
      names = {"-v", "--version"},
      description = "PrintScript version")
  String version;

  private StatementVisitor statementVisitor;
  private final LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();

  public static void runCli(File file, String version, Consumer<String> stdOut) {
    try {
      String sourceCode = readFile(file);
      Stream<Character> input = sourceCode.chars().mapToObj(intValue -> (char) intValue);

      LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();

      Lexer lexer = lexerFactory.createLexer(version);
      List<Token> tokens = lexer.lex(input);

      Parser parser = ParserFactoryImpl.newParserFactory().createParser(version);
      List<Statement> statements = parser.parse(tokens);

      InterpreterMemory interpreterMemory = new InterpreterMemory();
      BinaryExpressionResolver expressionHelper = new BinaryResolverComparisonImpl();
      ExpressionVisitor expressionVisitor =
          new ExpressionVisitorImpl(interpreterMemory, expressionHelper);
      VariableStatementResolver variableStatementResolver =
          new VariableStatementResolverBooleanImpl();

      StatementVisitor statementVisitor =
          new StatementVisitorImpl(
              expressionVisitor, interpreterMemory, variableStatementResolver, stdOut);

      Interpreter interpreter = new InterpreterImplementation(statementVisitor);
      interpreter.interpret(statements);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Integer call() throws Exception {
    try {
      String sourceCode = readFile(this.filePath);
      Stream<Character> input = sourceCode.chars().mapToObj(intValue -> (char) intValue);

      Lexer lexer = this.lexerFactory.createLexer(this.version);
      List<Token> tokens = lexer.lex(input);

      Parser parser = ParserFactoryImpl.newParserFactory().createParser(version);
      List<Statement> statements = parser.parse(tokens);

      if (this.executionMode.toLowerCase().equals("interpretation")) {
        this.initHelpers();
        Interpreter interpreter = new InterpreterImplementation(statementVisitor);
        interpreter.interpret(statements);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return 1;
    }

    return 0;
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new Cli()).execute(args);
    System.exit(exitCode);
  }

  private static String readFile(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)));
  }

  public static String readFile(File file) throws FileNotFoundException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    return br.lines().collect(Collectors.joining("\n"));
  }

  private void initHelpers() {
    InterpreterMemory interpreterMemory = new InterpreterMemory();
    BinaryExpressionResolver expressionHelper = new BinaryResolverComparisonImpl();
    ExpressionVisitor expressionVisitor =
        new ExpressionVisitorImpl(interpreterMemory, expressionHelper);
    VariableStatementResolver variableStatementResolver =
        new VariableStatementResolverBooleanImpl();

    this.statementVisitor =
        new StatementVisitorImpl(expressionVisitor, interpreterMemory, variableStatementResolver);
  }
}
