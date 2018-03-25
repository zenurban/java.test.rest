package zen.rest

import com.jayway.restassured.RestAssured
import org.springframework.http.HttpStatus
import spock.lang.Specification
import zen.rest.common.Common

class MathRestTest extends Specification {
    def setupSpec() {
        Common.startRestServer();
    }

    def cleanupSpec() {
        Common.stopRestServer();
    }

    def setup() {
    }

    def cleanup() {
    }

    def "test calculation"() {
        when:
        def cmd = "/calc/20+20"
        def response = RestAssured.given()
                .get(cmd)
        def result = response.body.asString()
        Common.log "20 +20 = $result"

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        result == '40'

        when:
        cmd = "/calc/(2+2)*3"
        response = RestAssured.given()
                .get(cmd)
        result = response.body.asString()

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        result == '12'

        when:
        cmd = "/calc/2 + 2 *3"
        response = RestAssured.given()
                .get(cmd)
        result = response.body.asString()

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        result == '8'

    }

}