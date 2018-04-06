package zen.parser

import spock.lang.Specification

class MathParserTest extends Specification {

    def "Math parser evaluate"() {

        when:
        String expression = "11 + (Math.exp(2.010635 + Math.sin(Math.PI/2)*3) + 50) / 2";
        def eval = MathParser.evaluate("10 + 2 * 6")

        then:
        eval == 22

        when:
        eval = MathParser.evaluate("( 10 + 2 ) * 6")
        then:
        eval == 72

        when: "no space after/before paranthesis"
        eval = MathParser.evaluate("(10 + 2) * 6")
        then:
        thrown IllegalArgumentException

        when: "divided by 0"
        eval = MathParser.evaluate("(10 + 2) * 6 /0")
        then:
        final  UnsupportedOperationException e = thrown()
        e.message == "Cannot divide by zero"

    }

    def "Script evaluate"() {

        when:
        String expression = "11 + (Math.exp(2.010635 + Math.sin(Math.PI/2)*3) + 50) / 2";
        def eval = ScriptEvaluator.evaluate(expression)

        then:
        eval == "110.99997794278411"

        when:
        eval = ScriptEvaluator.evaluate("(22+3)*4")
        then:
        eval == "100"
    }


}