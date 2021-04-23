package interpreter.factory;

import interpreter.Interpreter;
import visitor.StatementVisitor;

public interface InterpreterFactory {

  static InterpreterFactory newParserFactory() {
    return new InterpreterFactoryImpl();
  }

  Interpreter createInterpreter(StatementVisitor statementVisitor);
}
