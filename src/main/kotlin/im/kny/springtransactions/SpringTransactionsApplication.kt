package im.kny.springtransactions

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication()
@ComponentScan(basePackages = ["im.kny"])
class SpringTransactionsApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringTransactionsApplication::class.java, *args)
}
