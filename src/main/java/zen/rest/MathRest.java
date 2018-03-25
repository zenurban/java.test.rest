package zen.rest;


import org.springframework.stereotype.Component;
import zen.parser.ScriptEvaluator;

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
		return ScriptEvaluator.evaluate(expression);
	}

}
