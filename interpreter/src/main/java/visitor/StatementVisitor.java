package visitor;


import statement.Statement;
import statement.impl.*;

public interface StatementVisitor {
    void visitExpressionStatement(ExpressionStatement statement);
    void visitPrintStatement(PrintStatement statement);
    void visitVariableStatement(VariableStatement statement);
}
