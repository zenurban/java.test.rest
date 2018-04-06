package zen.parser.math

import spock.lang.Specification


class ParserTest extends Specification {
    def "math"() {
        when:
        String exprstr = "sizeof('abc 2')";
        Parser parser = new Parser();

        ExpressionNode expr = parser.parse(exprstr);
        double result = expr.getValue()
        println("$exprstr = $result");

        then:
        result == 5

        when:
        exprstr = "abs(-2)^(2*2) + abs(-4*2) + sizeof''";
        parser = new Parser();

        expr = parser.parse(exprstr);
        result = expr.getValue()
        println("$exprstr = $result");

        then:
        result == 24

        when:
        exprstr = "1 - (-2^2) - 1";
        parser = new Parser();

        expr = parser.parse(exprstr);
        result = expr.getValue()
        println("$exprstr = $result");

        then:
        result == 4

        when:
        exprstr = "blah blah";
        parser = new Parser();

        expr = parser.parse(exprstr);
        result = expr.getValue()
        println("$exprstr = $result");

        then:
        final ParserException e = thrown()
        e.message == "Unexpected symbol blah found"


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