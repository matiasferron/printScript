package statement.impl;

import expression.Expression;
import java.util.List;

import lombok.ToString;
import statement.Statement;
import visitor.StatementVisitor;

@ToString()
public class IfStatement implements Statement {

  private final Expression condition;
  private final List<Statement> conditionStatement;
  private final List<Statement> elseStatement;

  public IfStatement(
      Expression condition, List<Statement> conditionStatement, List<Statement> elseStatement) {
    this.condition = condition;
    this.conditionStatement = conditionStatement;
    this.elseStatement = elseStatement;
  }

  @Override
  public Expression getExpression() {
    return condition;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitIfStatement(this);
  }

  public Expression getCondition() {
    return condition;
  }

  public List<Statement> getConditionStatement() {
    return conditionStatement;
  }

  public List<Statement> getElseStatement() {
    return elseStatement;
  }

//  void addConditionBranchStatement(Statement statement) {
//    conditionStatement.add(statement);
//  }
//
//  void addElseBranchStatement(Statement statement) {
//    elseStatement.add(statement);
//  }

//  public String toString(){
//    return  getExpression().toString() + "If{" + conditionStatement.toString() + "} else{"+ elseStatement.toString() +"}";
//  }
}
