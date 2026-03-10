package im.kny.functionaltx.txmanager

import im.kny.functionaltx.Image
import jakarta.persistence.EntityManager

class ImageDao(val entityManager: EntityManager) {
    fun persist(image: Image) : Image {
        entityManager.persist(image)
        return image
    }
}