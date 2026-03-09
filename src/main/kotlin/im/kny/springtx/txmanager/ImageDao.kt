package im.kny.springtx.txmanager

import im.kny.springtx.Image
import jakarta.persistence.EntityManager

class ImageDao(val entityManager: EntityManager) {
    fun persist(image: Image) : Image {
        entityManager.persist(image)
        return image
    }
}