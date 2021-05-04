package interpreter.factory;

import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import interpreter.helper.InterpreterMemory;
import visitor.*;
import visitor.ExpressionVisitorResolvers.BinaryExpressionResolver;
import visitor.ExpressionVisitorResolvers.BinaryResolverComparisonImpl;
import visitor.StatementVisitorResolvers.VariableStatementResolver;
import visitor.StatementVisitorResolvers.VariableStatementResolverBooleanImpl;

public class InterpreterFactoryImpl implements InterpreterFactory {

  @Override
  public Interpreter createInterpreter(String version, InterpreterMemory interpreterMemory) {
    if (version.equals("1.1")) {
      return createIfAndBooleanInterpreter(interpreterMemory);
    }
    return createSimpleInterpreter(interpreterMemory);
  }

  private Interpreter createSimpleInterpreter(InterpreterMemory interpreterMemory) {
    BinaryExpressionResolver binaryResolverComparisonImpl = new BinaryResolverComparisonImpl();
    ExpressionVisitor expressionVisitor =
        new ExpressionVisitorImpl(interpreterMemory, binaryResolverComparisonImpl);

    VariableStatementResolver VariableStatementResolverBooleanImpl =
        new VariableStatementResolverBooleanImpl();
    StatementVisitor statementVisitor =
        new SimpleStatementVisitorImpl(
            expressionVisitor, interpreterMemory, VariableStatementResolverBooleanImpl);
    return new InterpreterImplementation(statementVisitor);
  }

  private Interpreter createIfAndBooleanInterpreter(InterpreterMemory interpreterMemory) {

    BinaryExpressionResolver binaryResolverComparisonImpl = new BinaryResolverComparisonImpl();
    ExpressionVisitor expressionVisitor =
        new ExpressionVisitorImpl(interpreterMemory, binaryResolverComparisonImpl);

    VariableStatementResolver VariableStatementResolverBooleanImpl =
        new VariableStatementResolverBooleanImpl();
    StatementVisitor statementVisitor =
        new StatementVisitorImpl(
            expressionVisitor, interpreterMemory, VariableStatementResolverBooleanImpl);

    return new InterpreterImplementation(statementVisitor);
  }
}
