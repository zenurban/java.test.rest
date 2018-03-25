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

Petr Štembera
ArcSight Engineering Manager
Micro Focus
Za Brumlovkou 1559/5
140 00 Prague 4, Czech republic
(M) +420 606.625.752

---                            
Two parser implementations: MathParser and SctiptEvaluator
are published via REST interface reachable
thru  localhost:11080/zen/calc/(2+2)*3 in the browser returns 12
when test jetty server is started
from command line(project root): './gradlew runJetty'

'./gradlew clean build' builds and tests the system.

Spock framework is used for testing, see test groovy

Dependencies can be derived from build.gradle
The project was developed and can be dynamically tested in Intellij IDEA.

