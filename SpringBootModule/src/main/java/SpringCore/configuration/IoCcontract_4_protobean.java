package SpringCore.configuration;

import SpringCore.bean.ProtoypeBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCcontract_4_protobean {
    // prototype bean
    @Bean(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    ProtoypeBean protoBean(){
        return new ProtoypeBean();
    }
}

// D. prototype scope
// Inject prototype into Singleton
