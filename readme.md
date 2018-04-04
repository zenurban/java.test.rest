Assignment Topic: Design and implement an service evaluating algebraic expressions in JAVA.

Deliverables: source codes, build script creating an war file deployable on an common servlet container (e.g. Tomcat)

Assignment:  Design an input format suitable for representation of algebraic expressions. (e.g. XML, JSON, plaintext prefix notation.. whatever).
The expression can contain:
  - integer constant
  - string constant
  - binary operators +,-,*,/
  - unary operator sizeof (string) – length of the string argument
  - unary operator abs(int)  - absolute value of the integer argument
Actual syntax does not matter ( element name "plus" suits well XML format while symbol "+" can be convenient for plaintext notations).

Then implement an service evaluating the input expression in Java using the technologies of your choice (REST, SOAP, plain network sockets, Spring, JAX-WS).
When invoked with a valid request the service will produce a response using the same format (result is either string or integer constant)

When evaluating the solution we well be interested in particular:
- object oriented design
- design patterns
- modular and extensible design
- maintainability of the delivered artefacts
- correct usage of the selected technologies
- justification of the selected technologies

---                            
###Four parser implementations: 
1. MathParser
2. RPN
3. ScriptEvaluator (JavaScript ScriptEngine)
4. Parser with Tokenizer

Parser is published via `REST interface` reachable
via e.g.  `localhost:11080/zen/calc/(2+2)*3` in the browser returns 12
when test jetty server is started
from command line(project root): 

`./gradlew runJetty`

`./gradlew clean build` builds and tests the system.

Spock framework is used for testing, see test groovy

Dependencies can be derived from build.gradle
The project was developed and can be dynamically tested in Intellij IDEA.

###Shunting-yard algorithm 
converts an expression given in conventional infix notation into Reverse Polish Notation:
```
For each token
{
    If (token is a number)
    {
        Add number to the output queue
    }
     
    If (token is an operator eg +,-,*...) 
    {
        While (stack not empty AND 
               stack top element is an operator)
        {
            If ((token = left associative AND 
                 precedence <= stack top element) OR
            (token = right associative AND 
                 precedence < stack top element))
            {
            Pop stack onto the output queue.  
                Exit while loop.
            }
        }
        Push token onto stack
    }
 
    If (token is left bracket '(')
    {
        Push token on to stack
    }
 
    If (token is right bracket ')')
    {
        While (stack not empty AND  
               stack top element not a left bracket)
        {
            Pop the stack onto output queue            
        }
        Pop the stack
    }
}
 
While (stack not empty)
{
    Pop stack onto output queue
}
```
####RPN evaluator
```
 For each token
 {
     If (token is a number)
     {
         Push value onto stack
     }
  
     If (token is an operator)
     {
         Pop 2 top values from the stack
         Evaluate operator using popped values as args
         Push result onto stack
     }
 }

```