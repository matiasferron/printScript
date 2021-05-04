package statement.impl;

import expression.Expression;
import lombok.ToString;
import statement.Statement;
import token.Token;
import token.TokenType;
import visitor.StatementVisitor;

@ToString()
public class VariableStatement implements Statement {

  private final Token name;
  private final Expression expression;
  private final TokenType type;
  private final Token keyWord;

  public VariableStatement(Token name, Expression expression, TokenType type, Token keyWord) {
    this.name = name;
    this.expression = expression;
    this.type = type;
    this.keyWord = keyWord;
  }

  @Override
  public void accept(StatementVisitor statementVisitor) {
    statementVisitor.visitVariableStatement(this);
  }

  public TokenType getType() {
    return type;
  }

  public Token getName() {
    return name;
  }

  @Override
  public Expression getExpression() {
    return expression;
  }

  public Token getKeyWord() {
    return keyWord;
  }

  //  public String toString(){
  //    return  name.getTokenValue() + "=" + expression.toString();
  //  }
}
