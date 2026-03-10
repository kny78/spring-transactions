package im.kny.functionaltx.spring


import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["im.kny.functionaltx"])
@EntityScan(basePackages = ["im.kny.functionaltx"])
class MySpringConfig {

}