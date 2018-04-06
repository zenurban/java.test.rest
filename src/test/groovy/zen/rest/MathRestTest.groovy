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
        def cmd = "/calc/20+20/10"
        def response = RestAssured.given()
                .get(cmd)
        def json = response.body.jsonPath()
        Common.log "20 +20/10 = $json.result"

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        json.result == '22'

        when:
        cmd = "/calc/(2+2)*3"
        response = RestAssured.given()
                .get(cmd)
        json = response.body.jsonPath()

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        json.result == '12'

        when:
        cmd = "/calc/2 + 2 *3"
        response = RestAssured.given()
                .get(cmd)
        json = response.body.jsonPath()

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        json.result == '8'

        when: 'multiple variables'
        cmd = "/calc/a + b  + c?a=1&b=2&c=3.4"
        response = RestAssured.given()
                .get(cmd)
        json = response.body.jsonPath()

        then: "expect OK status"
        response.statusCode == HttpStatus.OK.value()
        json.result == '6.4'

        when: 'expression error'
        cmd = "/calc/blah"
        response = RestAssured.given()
                .get(cmd)
        
        then: "expect BAD_REQUEST status"
        response.statusCode == HttpStatus.BAD_REQUEST.value()
        println("---->error.zen:" + response.body().asString())
        response.body().asString() =~ 'Expression error'

    }

}