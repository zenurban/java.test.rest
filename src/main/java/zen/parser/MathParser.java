package zen.parser;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 Parser to evaluate a given expression
 where tokens are separated  by space.
 */
public class MathParser {

	public static int evaluate(String expression) {
		char[] tokens = expression.toCharArray();

		// Stack for numbers: 'values'
		Stack<Integer> values = new Stack<>();

		// Stack for Operators: 'ops'
		Stack<Character> ops = new Stack<>();

		for (int i = 0; i < tokens.length; i++) {
			// Current token is a whitespace, skip it
			if (tokens[i] == ' ')
				continue;

			// Current token is a number, push it to stack for numbers
			if (isDigit(tokens[i])) {
				StringBuilder sb = new StringBuilder();
				// There may be more than one digits in number
				while (i < tokens.length && isDigit(tokens[i]) ) {
					sb.append(tokens[i++]);
				}
				values.push(Integer.parseInt(sb.toString()));
			}

			// Current token is an opening brace, push it to 'ops'
			else if (tokens[i] == '(')
				ops.push(tokens[i]);

				// Closing brace encountered, solve entire brace
			else if (tokens[i] == ')') {
				while (ops.peek() != '(')
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				ops.pop();
			}

			// Current token is an operator.
			else if (tokens[i] == '+' || tokens[i] == '-' ||
					tokens[i] == '*' || tokens[i] == '/') {
				// While top of 'ops' has same or greater precedence to current
				// token, which is an operator. Apply operator on top of 'ops'
				// to top two elements in values stack
				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));

				// Push current token to 'ops'.
				ops.push(tokens[i]);
			}
		}

		 /*
		 Entire expression has been parsed at this point, apply remaining
		 ops to remaining values
		 */
		try {
			while (!ops.empty())
				values.push(applyOp(ops.pop(), values.pop(), values.pop()));
		} catch (EmptyStackException e) {
			throw new IllegalArgumentException("The mathematical exception is malformed");
		}

		// Top of 'values' contains result, return it
		return values.pop();
	}

	private static boolean isDigit(char in)  {
		return in >= '0' && in <= '9';
	}


	// A utility method to apply an operator 'op' on operands 'a' 	and 'b'. Return the result.
	private static int applyOp(char op, int b, int a) {
		switch (op) {
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			case '/':
				if (b == 0)
					throw new
							UnsupportedOperationException("Cannot divide by zero");
				return a / b;
		}
		return 0;
	}

	// Returns true if 'op2' has higher or same precedence as 'op1'
	private static boolean hasPrecedence(char op1, char op2) {
		return op2 != '(' && op2 != ')' && ((op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-'));
	}
}