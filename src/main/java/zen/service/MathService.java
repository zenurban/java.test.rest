package zen.service;

import org.springframework.stereotype.Component;
import zen.parser.math.ExpressionNode;
import zen.parser.math.Parser;
import zen.parser.math.SetVariable;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Component
public class MathService {
    public String calculate(String input, MultivaluedMap<String, String> variableMap) throws Exception {
        Parser parser = new Parser();

        ExpressionNode expr = parser.parse(input);
        if (variableMap != null) {
            for (String key : variableMap.keySet()) {
                List<String> values = variableMap.get(key);
                System.out.println("zen.queryMap.key: " + key + ", value: " + values);
                expr.accept(new SetVariable(key, Double.parseDouble(values.get(0))));
            }
        }

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
