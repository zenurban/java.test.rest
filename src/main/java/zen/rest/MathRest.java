package zen.rest;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import zen.service.MathService;

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
        String result;

		try {
            result = (new MathService()).calculate(expression);
		} catch (Exception e) {
		    String msg = "Expression error: " + expression + " - " + e.getMessage();
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
		}

        CalcResponse calcResponse = new CalcResponse(expression, result);
        return Response.ok(MediaType.APPLICATION_JSON).entity(calcResponse).build();

	}


    @JsonAutoDetect
    class CalcResponse {
        @JsonProperty
		private String input;
        @JsonProperty
		private String result;

        CalcResponse(String input, String result) {
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
