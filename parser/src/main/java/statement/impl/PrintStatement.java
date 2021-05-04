package statement.impl;

import expression.Expression;
import lombok.ToString;
import statement.Statement;
import visitor.StatementVisitor;

@ToString()
public class PrintStatement implements Statement {

  private Expression expression;

  public PrintStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public void accept(StatementVisitor statementVisitor) {
    statementVisitor.visitPrintStatement(this);
  }

  @Override
  public Expression getExpression() {
    return expression;
  }
  //
  //  public String toString(){
  //    return "println(" + expression.toString() + ")";
  //  }
}
