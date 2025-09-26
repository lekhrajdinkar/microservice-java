package microservice.basicWebApp.jewelleryApp.custom;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class CustomIdentifier implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return UUID.randomUUID();
    }
}

/*
usage:

    @Id
    @GeneratedValue(generator = "myUUID")
    @GenericGenerator(name = "myUUID"
            ,strategy = "microservice.jewelleryApp.entities.CustomIdentifier"
            //,parameters = @Parameter(name = "prefix", value = "prod")
    )
 */
