package zen.rest.common

import spock.lang.Specification

class JettyStop extends Specification {

    def cleanupSpec() {
        Common.stopRestServer();
    }

}
