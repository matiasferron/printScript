package statement.parsers.statment.impl;

import exception.ParseException;
import expression.Expression;
import statement.Statement;
import statement.impl.VariableStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.statment.StatementParser;
import token.Token;
import token.TokenType;
import token.TokenWrapper;

import static token.TokenType.*;
import static token.TokenType.SEMICOLON;
import static utils.parserUtils.validateCurrentToken;
import static utils.parserUtils.tokenMatchTokenType;

public class VariableDeclarationParser extends StatementParser {

  public VariableDeclarationParser() {
    super();
  }

  public VariableDeclarationParser(CommonExpressionParser expressionParser) {
    super(expressionParser);
  }

  @Override
  public Statement parse(TokenWrapper tokens) {
    Token keyword = tokens.getCurrent();
    if (tokenMatchTokenType(tokens, LET, CONST)) {
      tokens.advance();
      Token name = validateCurrentToken(tokens, IDENTIFIER, "Expect variable name.");

      TokenType type = null;
      Expression initializer = null;

      if (tokenMatchTokenType(tokens, COLON)) {
        tokens.advance();
        type = checkTypeAssignation(tokens);
        tokens.advance();
      }
      if (tokenMatchTokenType(tokens, EQUALS)) {
        tokens.advance();
        initializer = expressionParser.parse(tokens);
      }

      validateCurrentToken(tokens, SEMICOLON, "Expect ';' after variable declaration.");
      return new VariableStatement(name, initializer, type, keyword);
    } else {
      // todo revisar. Ver que hacer en una situacion que no se le asigno un next parser y no
      // matcheo en la primea condicion
      if (nextParser == null) throw new ParseException("Parsing error", tokens.getCurrent());
      return nextParser.parse(tokens);
    }
  }

  private TokenType checkTypeAssignation(TokenWrapper tokens) {
    switch (tokens.getCurrent().getTokenType()) {
      case NUMBERTYPE:
        {
          return NUMBERTYPE;
        }
      case STRINGTYPE:
        {
          return STRINGTYPE;
        }
      case BOOLEAN:
        {
          return BOOLEAN;
        }
      default:
        {
          throw new ParseException("Need to specify variable type", tokens.getCurrent());
        }
    }
  }
}
