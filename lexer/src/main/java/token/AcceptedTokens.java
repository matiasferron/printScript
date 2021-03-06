package token;

import java.util.EnumMap;
import java.util.Set;

public class AcceptedTokens {
  private final EnumMap<TokenType, String> tokenFormats =
      new EnumMap<TokenType, String>(TokenType.class);
  private final String printScriptVersion;

  public AcceptedTokens(String printScriptVersion) {
    this.printScriptVersion = printScriptVersion;
    init();
  }

  private void init() {
    //    tokenFormats.put(TokenType.SPACE, " ");
    tokenFormats.put(TokenType.PLUS, "[+]");
    tokenFormats.put(TokenType.MINUS, "[-]");
    tokenFormats.put(TokenType.MULTIPLICATION, "[*]");
    tokenFormats.put(TokenType.DIVISION, "[/]");
    tokenFormats.put(TokenType.EQUALS, "=");
    tokenFormats.put(TokenType.LPAREN, "[(]");
    tokenFormats.put(TokenType.RPAREN, "[)]");
    tokenFormats.put(TokenType.LBRACKET, "[{]");
    tokenFormats.put(TokenType.RBRACKET, "[}]");
    tokenFormats.put(TokenType.NUMBERTYPE, "number");
    tokenFormats.put(TokenType.NUMBER, "-?[0-9.]+");
    tokenFormats.put(TokenType.STRINGTYPE, "string");
    tokenFormats.put(TokenType.LET, "let");
    tokenFormats.put(TokenType.COLON, ":");
    tokenFormats.put(TokenType.SEMICOLON, ";");
    tokenFormats.put(TokenType.PRINTLN, "println");
    tokenFormats.put(TokenType.STRING, "\"([_a-zA-Z0-9 !\\/.])*\"|'([_a-zA-Z0-9 !\\/.])*'");
    tokenFormats.put(TokenType.IDENTIFIER, "[_a-zA-Z][_a-zA-Z0-9]*");
    tokenFormats.put(TokenType.NEWLINE, "\n");
    //        tokenFormats.put(TokenType.EOF, "$");
    tokenFormats.put(TokenType.UNKNOWN, ",");

    if (this.printScriptVersion.equals("1.1")) {
      tokenFormats.put(TokenType.CONST, "const");
      tokenFormats.put(TokenType.BOOLEAN, "boolean");
      tokenFormats.put(TokenType.TRUE, "true");
      tokenFormats.put(TokenType.FALSE, "false");
      tokenFormats.put(TokenType.IF, "if");
      tokenFormats.put(TokenType.ELSE, "else");
      tokenFormats.put(TokenType.EQUALSEQUALS, "==");
      tokenFormats.put(TokenType.NOTEQUALS, "!=");
      tokenFormats.put(TokenType.NOT, "[!]");
      tokenFormats.put(TokenType.LESSEQ, "<=");
      tokenFormats.put(TokenType.GREATEREQ, ">=");
      tokenFormats.put(TokenType.GREATER, "[>]");
      tokenFormats.put(TokenType.LESS, "[<]");
    }
  }

  public Set<TokenType> getTokenTypes() {
    return tokenFormats.keySet();
  }

  public String getPatternByTokenType(TokenType tokenType) {
    return tokenFormats.get(tokenType);
  }
}
