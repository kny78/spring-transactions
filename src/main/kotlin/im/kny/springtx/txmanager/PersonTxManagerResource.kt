package im.kny.springtx.txmanager

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import im.kny.springtx.Image
import im.kny.springtx.Person
import im.kny.springtx.TxManagerImpl
import im.kny.springtx.annotation.PersonAnnotationResource
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Path("/persons-tx-manager")
@Component
class PersonTxManagerResource {

    @Inject
    lateinit var txManager: TxManagerImpl

    val objectMapper: ObjectMapper by lazy {
        ObjectMapper()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postPerson(name: String): Person {

        val person1 = txManager.autoCommitTx { dbCtx ->
            dbCtx.person.persist(Person(name, null))
        }

        val imageString = getSlowImage(person1.id!!)
        val person2 = txManager.autoCommitTx { dbCtx ->
            val image = dbCtx.image.persist(Image(imageString.toByteArray()))
            val personInTx = dbCtx.person.byId(person1.id)
            personInTx.image = image
            personInTx
        }

        return person2
    }

    private fun getSlowImage(personId: Long): String {

        if(slowImageDelay > 0) {
            println("Waiting for image")
            Thread.sleep(slowImageDelay)
            println("Got image")
        }
        return "Url for $personId"
    }

    companion object {
        var slowImageDelay = 10_000L
    }
}