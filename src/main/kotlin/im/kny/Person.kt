package im.kny

import jakarta.persistence.*

@Entity
@Table(name="person")
data class Person constructor(
    var name: String,
    var image: String?,
) {
    @Id
    @GeneratedValue(generator = "person_id_seq")
    @SequenceGenerator(name="person_id_seq", sequenceName = "person_id_seq", allocationSize = 1)
    var id: Long?=null
}
