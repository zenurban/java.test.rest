package zen.rest;


import org.springframework.stereotype.Component;
import zen.parser.math.ExpressionNode;
import zen.parser.math.Parser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/calc")
public class MathRest {

	@GET
	@Path("{exp}")
	@Produces(MediaType.APPLICATION_JSON)
	public String calc(
			@PathParam("exp") String expression
	) {
		Parser parser = new Parser();

		ExpressionNode expr = parser.parse(expression);
		double result = expr.getValue() ;

		return fmt2(result);

		//return ScriptEvaluator.evaluate(expression);
	}

	public String fmt(double d)
	{
        //return String.valueOf(result);
		if(d == (long) d)
			return String.format("%d",(long)d);
		else
			return String.format("%s",d);
	}

    public String fmt2(double d) {
        long i = (long) d;
        return d == i ? String.valueOf(i) : String.valueOf(d);
    }

}
