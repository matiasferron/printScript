package token;

public enum TokenType {

    //operators + - / * =
    PLUS, MINUS, DIVISION, MULTIPLICATION, EQUALS,

    //keywords let const println number string
    LET, CONST, PRINTLN, NUMBER, STRING,

    //symbols ( ) ; : ' " .
    LPAREN, RPAREN, SEMICOLON, COLON, SINGLE_QUOTE, DOUBLE_QUOTE, DOT,

    //other - any variable identifier - unknown token for error handling
    IDENTIFIER, NUMBER_TYPE, STRING_TYPE, UNKNOWN

    //eof?
}
