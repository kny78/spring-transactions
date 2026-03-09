package im.kny.springtx

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator

@Entity
data class Image(
    var blob: ByteArray?
) {

    @Id
    @GeneratedValue(generator = "image_id_seq")
    @SequenceGenerator(name="image_id_seq", sequenceName = "image_id_seq", allocationSize = 1)
    var id: Long? = null
}
