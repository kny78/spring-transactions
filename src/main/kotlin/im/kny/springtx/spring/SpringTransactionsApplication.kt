package im.kny.springtx.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTransactionsApplication

fun main(args: Array<String>) {
	runApplication<SpringTransactionsApplication>(*args)
}
