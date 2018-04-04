package zen.parser.math;

/**
 An interface for expression nodes.
 <p>
 Every concrete type of expression node has to implement this interface.
 */
public interface ExpressionNode {
    /** Node id for variable nodes */
    int VARIABLE_NODE = 1;
    /** Node id for constant nodes */
    int CONSTANT_NODE = 2;
    /** Node id for addition nodes */
    int ADDITION_NODE = 3;
    /** Node id for multiplication nodes */
    int MULTIPLICATION_NODE = 4;
    /** Node id for exponentiation nodes */
    int EXPONENTIATION_NODE = 5;
    /** Node id for function nodes */
    int FUNCTION_NODE = 6;

    /**
     Returns the type of the node.ExpressionNode
     <p>
     Each class derived from ExpressionNode representing a specific
     role in the expression should return the type according to that
     role.

     @return type of the node
     */
    public int getType();

    /**
     Calculates and returns the value of the sub-expression represented by
     the node.

     @return value of expression
     */
    public double getValue();

    /**
     Method needed for the visitor design pattern

     @param visitor the visitor
     */
    public void accept(ExpressionNodeVisitor visitor);

}
