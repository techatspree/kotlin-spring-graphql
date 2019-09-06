package de.akquinet.demo.graphql.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class PersonEntity(
        var name: String,
        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
        val address: AddressEntity,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "owner")
        val pets: List<PetEntity> = ArrayList(),
        @Id @GeneratedValue
        var id: Long? = null
)

@Entity
class AddressEntity(
        var street: String,
        var zipCode: String,
        var city: String,
        @Id @GeneratedValue
        var id: Long? = null
)


@Entity
class PetEntity(
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        var owner: PersonEntity,
        var name: String,
        @Enumerated(EnumType.STRING)
        var type: PetType,
        @Id @GeneratedValue
        var id: Long? = null
)


enum class PetType {
    CAT,
    DOG,
    BIRD
}
