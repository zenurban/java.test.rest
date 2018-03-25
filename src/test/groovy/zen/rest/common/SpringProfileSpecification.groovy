package zen.rest.common

import spock.lang.Specification
//@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
//@ContextConfiguration(locations = "classpath:applicationContextDaolTest.xml")
//@Transactional("transactionManager")
abstract class SpringProfileSpecification extends Specification {
    def setupSpec() {
/*
        Resource resource = new ClassPathResource("Configuration.properties")
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
*/

    }

}
