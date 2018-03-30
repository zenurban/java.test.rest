package zen.kt.pattern.creation

import spock.lang.Specification

class AllTest extends Specification {
    
    def "factory"() {

        when:
        def plantFactory = PlantFactory.Companion

        then:
        plantFactory == PlantFactory.Companion
        
    }


}