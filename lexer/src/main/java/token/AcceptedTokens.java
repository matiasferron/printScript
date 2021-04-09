package token;

import java.util.EnumMap;
import java.util.Set;

public class AcceptedTokens {
    private final EnumMap<TokenType, String> tokenFormats = new EnumMap<TokenType, String>(TokenType.class);

    public AcceptedTokens() {
        init();
    }

    private void init() {
        tokenFormats.put(TokenType.PLUS , "[+]");
        tokenFormats.put(TokenType.MINUS , "[-]");
        tokenFormats.put(TokenType.MULTIPLICATION , "[*]");
        tokenFormats.put(TokenType.DIVISION, "[/]");
        tokenFormats.put(TokenType.EQUALS , "=");
        tokenFormats.put(TokenType.LPAREN , "[(]");
        tokenFormats.put(TokenType.RPAREN , "[)]");
        tokenFormats.put(TokenType.NUMBER_TYPE , "number");
        tokenFormats.put(TokenType.NUMBER, "-?[0-9.]+");
        tokenFormats.put(TokenType.STRING_TYPE , "string");
        tokenFormats.put(TokenType.LET , "let");
        tokenFormats.put(TokenType.CONST , "const");
        tokenFormats.put(TokenType.COLON , ":");
        tokenFormats.put(TokenType.SEMICOLON , ";");
        tokenFormats.put(TokenType.PRINTLN, "println");
        tokenFormats.put(TokenType.STRING, "\"([_a-zA-Z0-9 !\\/.])*\"|'([_a-zA-Z0-9 !\\/.])*'");
        tokenFormats.put(TokenType.IDENTIFIER , "[_a-zA-Z][_a-zA-Z0-9]*");
//        tokenFormats.put(TokenType.NEWLINE,"\n");
//        tokenFormats.put(TokenType.EOF, "$");
        tokenFormats.put(TokenType.UNKNOWN , ",");
    }

    public Set<TokenType> getTokenTypes() {
        return tokenFormats.keySet();
    }

    public String getPatternByTokenType(TokenType tokenType) {
        return tokenFormats.get(tokenType);
    }
}
