package zen.parser.math

import spock.lang.Specification


class ParserTest extends Specification {
    def "math"() {
        when:
        String exprstr = "2^(2*2) + 4*2";
        Parser parser = new Parser();

        ExpressionNode expr = parser.parse(exprstr);
        double result = expr.getValue()
        println("$exprstr = $result");

        then:
        result == 24

        when:
        exprstr = "2*(1+sin(pi/2))^2";
        parser = new Parser();
        expr = parser.parse(exprstr);
        expr.accept(new SetVariable("pi", Math.PI));
        result = expr.getValue()
        println("$exprstr = $result");

        then:
        result == 8

    }

}