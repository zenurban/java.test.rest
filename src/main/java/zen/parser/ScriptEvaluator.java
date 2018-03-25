package zen.parser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEvaluator {
	private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

	private ScriptEvaluator() {
	}

	public static String evaluate(String expression) {
		Object eval = null;
		try {
			eval = engine.eval(expression);
		} catch (ScriptException e) {
			throw new IllegalArgumentException("The mathematical exception is malformed");
		}

		return  eval.toString();
	}
}
