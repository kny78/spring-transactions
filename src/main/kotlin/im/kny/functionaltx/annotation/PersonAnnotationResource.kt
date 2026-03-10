package im.kny.functionaltx.annotation

import im.kny.functionaltx.Person
import im.kny.functionaltx.txmanager.PersonTxManagerResource
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Path("/persons-annotations")
@Component
class PersonAnnotationResource @Autowired constructor(
    val personAnnotationTransactionService: PersonAnnotationTransactionService
) {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postPerson(personName: String): Person {
        val donald = personAnnotationTransactionService.persistPerson(Person(personName, null))
        val image = getSlowImage(donald.id!!)
        val donald2 = personAnnotationTransactionService.updateImageOnPerson(donald.id, image)

        return donald2
    }

    private fun getSlowImage(personId: Long): String {
        if(slowImageDelay > 0) {
            println("Waiting for image")
            Thread.sleep(PersonTxManagerResource.slowImageDelay)
            println("Got image")
        }
        return "Url for $personId"
    }

    companion object{
        var slowImageDelay = 1_000L
    }
}