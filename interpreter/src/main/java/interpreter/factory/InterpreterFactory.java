package interpreter.factory;

import interpreter.Interpreter;
import interpreter.helper.InterpreterMemory;

public interface InterpreterFactory {

  static InterpreterFactory newParserFactory() {
    return new InterpreterFactoryImpl();
  }

  Interpreter createInterpreter(String version, InterpreterMemory interpreterMemory);
}
