package im.kny.springtx.annotation

import im.kny.springtx.Image
import im.kny.springtx.Person
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PersonAnnotationTransactionService @Autowired constructor(
    val personAnnotationDao: PersonAnnotationDao,
    val imageAnnotationImage: ImageAnnotationDao
) {

    @Transactional
    fun persistPerson(person: Person): Person {
        return personAnnotationDao.persist(person)
    }

    @Transactional
    fun updateImageOnPerson(personId: Long?, imageString: String): Person {
        val donaldInTx = personAnnotationDao.byId(personId)
        val image = Image(imageString.toByteArray())
        imageAnnotationImage.persist(image)
        donaldInTx.image = image
        return donaldInTx
    }
}