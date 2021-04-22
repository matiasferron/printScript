import lexer.Lexer;
import lexer.factory.LexerFactory;
import lexer.factory.LexerFactoryImpl;
import picocli.CommandLine;
import token.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@CommandLine.Command
public class Cli implements Runnable {

  @CommandLine.Option(
      names = {"-f", "--file"},
      description = "file path",
      required = true)
  private String filePath;

  @CommandLine.Option(
      names = {"-m", "--mode"},
      description = "execution mode",
      required = true)
  String runMode;

  @CommandLine.Option(
      names = {"-v", "--version"},
      description = "file version")
  String fileVersion;

  public static void main(String[] args) {
    CommandLine.run(new Cli(), args);
  }

  private LexerFactory lexerFactory = LexerFactoryImpl.newLexerFactory();

  @Override
  public void run() {

    if (!(runMode.equals("interpretation") || runMode.equals("validation"))) {
      System.out.println("Invalid run mode");
      System.exit(1);
    }

    try {
      Stream<Character> input = readFile(filePath).chars().mapToObj(i -> (char) i);

      Lexer lexer = lexerFactory.createLexer();
      List<Token> tokenList = lexer.lex(input);

      // Statements = parser.parse(tokenList)

      if (runMode.equals("interpretation")) {

        // interpreter.executeCode(code)
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String readFile(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)));
  }
}
