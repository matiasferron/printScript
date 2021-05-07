package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import token.*;

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

    ArrayList<Token> tmp = new ArrayList<>();

    for (int i = 0; i < resultTokens.size(); i++) {
      TokenType t = resultTokens.get(i).getTokenType();
      if (t == TokenType.NUMBERTYPE || t == TokenType.STRINGTYPE || t == TokenType.BOOLEAN) {
        if (i + 1 < resultTokens.size()) {
          if (resultTokens.get(i + 1).getTokenType() == TokenType.IDENTIFIER) {
            tmp.add(
                tokenFactory.create(
                    TokenType.IDENTIFIER,
                    resultTokens.get(i).getTokenValue() + resultTokens.get(i + 1).getTokenValue(),
                    resultTokens.get(i).getPosition()));
          } else {
            tmp.add(resultTokens.get(i));
          }
        }
      } else {
        if (t == TokenType.GREATER || t == TokenType.LESS) {
          if (i + 1 < resultTokens.size()) {
            if (resultTokens.get(i + 1).getTokenType().equals(TokenType.EQUALS)) {
              if (t == TokenType.GREATER) {
                tmp.add(
                    tokenFactory.create(
                        TokenType.GREATEREQ,
                        resultTokens.get(i).getTokenValue()
                            + resultTokens.get(i + 1).getTokenValue(),
                        resultTokens.get(i).getPosition()));
              } else {
                tmp.add(
                    tokenFactory.create(
                        TokenType.LESSEQ,
                        resultTokens.get(i).getTokenValue()
                            + resultTokens.get(i + 1).getTokenValue(),
                        resultTokens.get(i).getPosition()));
              }
            } else {
              tmp.add(resultTokens.get(i));
            }
          }
        } else {
          tmp.add(resultTokens.get(i));
        }
      }
    }
    for (int x = 0; x < tmp.size(); x++) {
      if (x + 1 < tmp.size()) {
        if (tmp.get(x).getTokenType().equals(TokenType.IDENTIFIER)
            && tmp.get(x + 1).getTokenType().equals(TokenType.IDENTIFIER)) {
          tmp.remove(x + 1);
        } else if ((tmp.get(x).getTokenType().equals(TokenType.GREATEREQ)
            || tmp.get(x).getTokenType().equals(TokenType.LESSEQ)
                && tmp.get(x + 1).getTokenType().equals(TokenType.EQUALS))) {
          tmp.remove(x + 1);
        }
      }
    }

    // resultTokens.add(tokenFactory.create(TokenType.EOF, "",new Position(this.line,
    // this.column)));
    return this.filterNewLine(tmp);
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

  private ArrayList<Token> filterNewLine(List<Token> tokens) {
    return new ArrayList<>(
        tokens.stream()
            .filter(t -> !t.getTokenType().equals(TokenType.NEWLINE))
            .collect(Collectors.toList()));
  }
}
