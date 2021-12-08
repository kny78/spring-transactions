package im.kny.springtransactions

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTransactionsApplication

fun main(args: Array<String>) {
	runApplication<SpringTransactionsApplication>(*args)
}
