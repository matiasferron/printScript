package interpreter.factory;

import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import visitor.StatementVisitor;

public class InterpreterFactoryImpl implements InterpreterFactory{

    @Override
    public Interpreter createInterpreter(StatementVisitor statementVisitor) {
        return new InterpreterImplementation(statementVisitor);
    }
}
