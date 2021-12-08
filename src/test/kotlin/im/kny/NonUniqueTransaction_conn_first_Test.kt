package im.kny

import im.kny.springtransactions.MySpringConfig
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MySpringConfig::class]
)
class NonUniqueTransaction_conn_first_Test @Autowired constructor(
    val txManager: TxManager
) {


    @Test
    fun manyJobs() {

        val jobs = mutableListOf<Deferred<Unit>>()
        runBlocking(Dispatchers.Unconfined) {
            LOG.info("before")
            for (i in 1..3) {
                LOG.info("Starting $i")
                jobs += async { runForFun(i) }
            }

            jobs.forEach { job -> job.await() }
        }
    }

    suspend fun runForFun(i: Int) {
        println("Nr $i")
        txManager.autoCommitTxSuspended { dbCtx ->
            val list = dbCtx.person.list()
            delay(500L)

            LOG.info("BackendPID: ${dbCtx.backendPID()}")
        }
        delay(1L)

        txManager.autoCommitTxSuspended { dbCtx ->
            dbCtx.person.list()
            delay(500L)
            LOG.info("BackendPID: ${dbCtx.backendPID()}")
        }
    }

    companion object {
        private val LOG: Logger by lazy(LazyThreadSafetyMode.NONE) { LoggerFactory.getLogger(javaClass.enclosingClass) }
    }
}