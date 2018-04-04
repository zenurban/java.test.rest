package zen.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 Shunting-yard algorithm converts an expression given in conventional infix notation into Reverse Polish Notation:
 
 For each token
 {
 If (token is a number)
 {
 Add number to the output queue
 }

 If (token is an operator eg +,-,*...)
 {
 While (stack not empty AND
 stack top element is an operator)
 {
 If ((token = left associative AND
 precedence <= stack top element) OR
 (token = right associative AND
 precedence < stack top element))
 {
 Pop stack onto the output queue.
 Exit while loop.
 }
 }
 Push token onto stack
 }

 If (token is left bracket '(')
 {
 Push token on to stack
 }

 If (token is right bracket ')')
 {
 While (stack not empty AND
 stack top element not a left bracket)
 {
 Pop the stack onto output queue
 }
 Pop the stack
 }
 }

 While (stack not empty)
 {
 Pop stack onto output queue
 }

 */
public class MathParser2 {
    // Associativity constants for operators
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;

    // Operators
    private static final Map<String, int[]> OPERATORS = new HashMap<>();

    static {
        // Map<"token", []{precendence, associativity}>
        OPERATORS.put("+", new int[]{0, LEFT_ASSOC});
        OPERATORS.put("-", new int[]{0, LEFT_ASSOC});
        OPERATORS.put("*", new int[]{5, LEFT_ASSOC});
        OPERATORS.put("/", new int[]{5, LEFT_ASSOC});
    }

    public static void main(String[] args) {
        String mathExpression = "( 1 + 2 ) * ( 3 / 4 ) - ( 5 + 6 )";
        String[] input = mathExpression.split(" ");
        String[] output = infixToRPN(input);

        // Build output RPN string minus the commas
        for (String token : output) {
            System.out.print(token + " ");
        }
        System.out.print("\n ");

        // Feed the RPN string to RPNtoDouble to give result
        Double result = RPNtoDouble(output);
        System.out.print(mathExpression + " = " + result);

    }

    // Convert infix expression format into reverse Polish notation
    private static String[] infixToRPN(String[] inputTokens) {
        ArrayList<String> out = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        // For each token
        for (String token : inputTokens) {
            // If token is an operator
            if (isOperator(token)) {
                // While stack not empty AND stack top element
                // is an operator
                while (!stack.empty() && isOperator(stack.peek())) {
                    if ((isAssociative(token, LEFT_ASSOC) &&
                            cmpPrecedence(token, stack.peek()) <= 0) ||
                            (isAssociative(token, RIGHT_ASSOC) &&
                                    cmpPrecedence(token, stack.peek()) < 0)) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack
                stack.push(token);
            }
            // If token is a left bracket '('
            else if (token.equals("(")) {
                stack.push(token);  //
            }
            // If token is a right bracket ')'
            else if (token.equals(")")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            }
            // If token is a number
            else {
                out.add(token);
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        String[] output = new String[out.size()];
        return out.toArray(output);
    }

    private static double RPNtoDouble(String[] tokens) {
        Stack<String> stack = new Stack<>();

        // For each token
        for (String token : tokens) {
            // If the token is a value push it onto the stack
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                // Token is an operator: pop top two entries
                Double d2 = Double.valueOf(stack.pop());
                Double d1 = Double.valueOf(stack.pop());

                //Get the result
                Double result = token.compareTo("+") == 0 ? d1 + d2 :
                        token.compareTo("-") == 0 ? d1 - d2 :
                                token.compareTo("*") == 0 ? d1 * d2 :
                                        d1 / d2;

                // Push result onto stack
                stack.push(String.valueOf(result));
            }
        }

        return Double.valueOf(stack.pop());
    }

    // Test if token is an operator
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    // Test associativity of operator token
    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }

        return OPERATORS.get(token)[1] == type;
    }

    // Compare precedence of operators.
    private static int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalid tokens: " + token1
                    + " " + token2);
        }
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }
}
