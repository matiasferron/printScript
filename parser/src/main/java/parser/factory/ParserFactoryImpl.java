package parser.factory;

import parser.Parser;
import parser.ParserImpl;
import statement.parsers.expression.*;
import statement.parsers.statment.StatementParser;
import statement.parsers.statment.impl.ExpressionStatementParser;
import statement.parsers.statment.impl.IfStatementParser;
import statement.parsers.statment.impl.VariableDeclarationParser;
import statement.parsers.statment.impl.printParser;

public class ParserFactoryImpl implements ParserFactory {

  public static ParserFactory newParserFactory() {
    return new ParserFactoryImpl();
  }

  @Override
  public Parser createParser(String version) {
    String PRINT_SCRIPT_VERSION = "1.1";

    if (version.equals(PRINT_SCRIPT_VERSION))
      return createIfAndBoolParser();

    return createBasicParser();
  }

  private Parser createIfAndBoolParser() {
    CommonExpressionParser assigmentExpressionParser = new AssigmentExpressionParser();
    CommonExpressionParser commonExpressionParser = new ComparisonParser();
    CommonExpressionParser additionParser = new AdditionParser();
    CommonExpressionParser multiplicationParser = new MultiplicationParser();
    CommonExpressionParser typeParser = new TypeParser();

    assigmentExpressionParser.setNextParser(commonExpressionParser);
    commonExpressionParser.setNextParser(additionParser);
    additionParser.setNextParser(multiplicationParser);
    multiplicationParser.setNextParser(typeParser);
    typeParser.setNextParser(assigmentExpressionParser);

    StatementParser variableDeclarationParser = new VariableDeclarationParser(assigmentExpressionParser);
    StatementParser printParser = new printParser(assigmentExpressionParser);
    StatementParser expressionStatementParser = new ExpressionStatementParser(assigmentExpressionParser);
    StatementParser ifStatementParser = new IfStatementParser(assigmentExpressionParser);

    ifStatementParser.setNextParser(variableDeclarationParser);
    variableDeclarationParser.setNextParser(printParser);
    printParser.setNextParser(expressionStatementParser);

    return new ParserImpl(ifStatementParser);
  }

  private Parser createBasicParser() {
    CommonExpressionParser assigmentExpressionParser = new AssigmentExpressionParser();
    CommonExpressionParser additionParser = new AdditionParser();
    CommonExpressionParser multiplicationParser = new MultiplicationParser();
    CommonExpressionParser typeParser = new TypeParser();

    assigmentExpressionParser.setNextParser(additionParser);
    additionParser.setNextParser(multiplicationParser);
    multiplicationParser.setNextParser(typeParser);
    typeParser.setNextParser(assigmentExpressionParser);

    StatementParser variableDeclarationParser = new VariableDeclarationParser(assigmentExpressionParser);
    StatementParser printParser = new printParser(assigmentExpressionParser);
    StatementParser expressionStatementParser = new ExpressionStatementParser(assigmentExpressionParser);

    variableDeclarationParser.setNextParser(printParser);
    printParser.setNextParser(expressionStatementParser);

    return new ParserImpl(variableDeclarationParser);
  }
}
