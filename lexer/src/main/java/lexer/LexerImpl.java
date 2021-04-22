package lexer;

import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

  private final AcceptedTokens acceptedTokens;
  private final TokenFactory tokenFactory;

  private int line = 0;
  private int column = 0;

  public LexerImpl(String printScriptVersion) {
    this.tokenFactory = TokenFactory.newTokenFactory();
    this.acceptedTokens = new AcceptedTokens(printScriptVersion);
  }

  @Override
  public List<Token> lex(Stream<Character> stream) {

    ArrayList<Token> resultTokens = new ArrayList<>();

    Matcher matcher = getMatcher(stream);

    while (matcher.find()) {
      for (TokenType tokenType : acceptedTokens.getTokenTypes()) {
        if (matcher.group(tokenType.name()) != null) {
          Token token =
              tokenFactory.create(
                  tokenType, matcher.group(tokenType.name()), new Position(this.line, this.column));
          resultTokens.add(token);
          this.column += matcher.group(tokenType.name()).length();

          if (isNewLine(token)) {
            this.column = 0;
            this.line++;
          }
        }
      }
    }

    //        resultTokens.add(tokenFactory.create(TokenType.EOF, "",new Position(this.line,
    // this.column)));
    return resultTokens;
  }

  private Matcher getMatcher(Stream<Character> input) {
    StringBuilder tokenPatternsBuffer = new StringBuilder();
    for (TokenType tokenType : acceptedTokens.getTokenTypes()) {
      tokenPatternsBuffer.append(
          String.format(
              "|(?<%s>%s)", tokenType.name(), acceptedTokens.getPatternByTokenType(tokenType)));
    }

    return Pattern.compile(tokenPatternsBuffer.substring(1))
        .matcher(input.map(Objects::toString).collect(Collectors.joining()));
  }

  private boolean isNewLine(Token token) {
    return token.getTokenType() == TokenType.NEWLINE;
  }
}
