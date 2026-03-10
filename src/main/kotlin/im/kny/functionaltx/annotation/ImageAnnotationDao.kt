package im.kny.functionaltx.annotation

import im.kny.functionaltx.Image
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ImageAnnotationDao @Autowired constructor(val entityManager: EntityManager){

    fun persist(image: Image) : Image {
        entityManager.persist(image)
        return image
    }

}