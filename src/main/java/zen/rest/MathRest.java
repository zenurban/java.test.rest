package zen.rest;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import zen.parser.math.ExpressionNode;
import zen.parser.math.Parser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/calc")
@Produces("application/json")
public class MathRest {

	@GET
	//enable slashes in path params:
    @Path("{exp : (.+)?}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response calc(
			@PathParam("exp") String expression
	) {
		Parser parser = new Parser();

		double result = 0;
		try {
			ExpressionNode expr = parser.parse(expression);
			result = expr.getValue();
		} catch (Exception e) {
		    String msg = "Expression error: " + expression + " - " + e.getMessage();
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
			//return msg;
		}

		//return fmt2(result);
		CalcResponse calcResponse = new CalcResponse(expression, fmt2(result));
		String response = fmt2(result);
        //return Response.ok( response, MediaType.APPLICATION_JSON).build();
        return Response.ok(  MediaType.APPLICATION_JSON).entity(calcResponse).build();

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


    @JsonAutoDetect
    class CalcResponse {
        @JsonProperty
		private String input;
        @JsonProperty
		private String result;

		public CalcResponse(String input, String result) {
			this.input = input;
			this.result = result;
		}

		String getInput() {
			return input;
		}

        String getResult() {
			return result;
		}

	}

}
