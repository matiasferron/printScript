import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import interpreter.helper.InterpreterMemory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
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

// import lexer.Lexer;
// import lexer.factory.LexerFactory;
// import lexer.factory.LexerFactoryImpl;
// import picocli.CommandLine;
// import token.Token;
//
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.List;
// import java.util.stream.Stream;
//
// @CommandLine.Command
// public class Cli implements Runnable {
//
//  @CommandLine.Option(
//      names = {"-f", "--file"},
//      description = "file path",
//      required = true)
//  private String filePath;
//
//  @CommandLine.Option(
//      names = {"-m", "--mode"},
//      description = "execution mode",
//      required = true)
//  String runMode;
//
//  @CommandLine.Option(
//      names = {"-v", "--version"},
//      description = "printscript version")
//  String version;
//
//  public static void main(String[] args) {
//    CommandLine.run(new Cli(), args);
//  }
//
//  private LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();
//
//  @Override
//  public void run() {
//
//    if (!(runMode.equals("interpretation") || runMode.equals("validation"))) {
//      System.out.println("Invalid run mode");
//      System.exit(1);
//    }
//
//    try {
//      Stream<Character> input = readFile(filePath).chars().mapToObj(i -> (char) i);
//
//      Lexer lexer = lexerFactory.createLexer(version);
//      List<Token> tokenList = lexer.lex(input);
//
//      // Statements = parser.parse(tokenList)
//
//      if (runMode.equals("interpretation")) {
//
//        // interpreter.executeCode(code)
//      }
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  private static String readFile(String path) throws IOException {
//    return new String(Files.readAllBytes(Paths.get(path)));
//  }
// }

// op2
// public class Cli {
//    public static void main(String[] args) {
//        new CommandLine(new Tar()).execute(args);
//    }
// }

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

  @Override
  public Integer call() throws Exception {
    try {
      String sourceCode = readFile(this.filePath);
      Stream<Character> input = sourceCode.chars().mapToObj(intValue -> (char) intValue);

      Lexer lexer = this.lexerFactory.createLexer(this.version);
      List<Token> tokens = lexer.lex(input);

      Parser parser = ParserFactoryImpl.newParserFactory().createParser("1.1");
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
