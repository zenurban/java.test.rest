package zen.parser.math;

/**
 An interface for the visitor design pattern.
 <p>
 Expression nodes can be visited by ExpressionNodeVisitor by calling their
 accept methods. The expression nodes, in turn, call the appropriate visit
 method of the expression node visitor.
 */
public interface ExpressionNodeVisitor {
    /** Visit a VariableExpressionNode */
    void visit(VariableExpressionNode node);

    /** Visit a ConstantExpressionNode */
    void visit(ConstantExpressionNode node);

    /** Visit a AdditionExpressionNode */
    void visit(AdditionExpressionNode node);

    /** Visit a MultiplicationExpressionNode */
    void visit(MultiplicationExpressionNode node);

    /** Visit a ExponentiationExpressionNode */
    void visit(ExponentiationExpressionNode node);

    /** Visit a FunctionExpressionNode */
    void visit(FunctionExpressionNode node);
}
