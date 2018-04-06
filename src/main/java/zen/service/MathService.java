package zen.service;

import zen.parser.math.ExpressionNode;
import zen.parser.math.Parser;

public class MathService {
    public String calculate(String input) throws Exception {
        Parser parser = new Parser();

        ExpressionNode expr = parser.parse(input);
        double result = expr.getValue();

        return fmt2(result);
    }

    private String fmt(double d) {
        //return String.valueOf(result);
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    private String fmt2(double d) {
        long i = (long) d;
        return d == i ? String.valueOf(i) : String.valueOf(d);
    }
}
