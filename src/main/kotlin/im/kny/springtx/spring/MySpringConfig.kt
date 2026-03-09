package im.kny.springtx.spring


import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["im.kny.springtx"])
@EntityScan(basePackages = ["im.kny.springtx"])
class MySpringConfig {

}