package de.akquinet.demo.graphql.api.resolver.person

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import de.akquinet.demo.graphql.model.AddressEntity
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


data class AddressCreateDto(
        val street: String,
        val zipCode: String,
        val city: String
)

data class AddressUpdateDto(
        val street: String?,
        val zipCode: String?,
        val city: String?
)

data class PersonCreateDto(
        val name: String,
        val address: AddressCreateDto)


data class PersonUpdateDto(
        val name: String?,
        val address: AddressUpdateDto?)


@Component
class PersonMutationResolver @Autowired constructor(private val personRepository: PersonRepository) : GraphQLMutationResolver {

    fun createPerson(dto: PersonCreateDto): PersonEntity {
        return personRepository.saveAndFlush(PersonEntity(
                dto.name,
                AddressEntity(dto.address.street, dto.address.zipCode, dto.address.city))
        )
    }

    fun updatePerson(personId: Long, dto: PersonUpdateDto): PersonEntity {
        val person = personRepository.getOne(personId)

        dto.name?.let {
            person.name = it
        }

        dto.address?.let { address: AddressUpdateDto ->
            address.street?.let {
                person.address.street = it
            }
            address.city?.let {
                person.address.city = it
            }
            address.zipCode?.let {
                person.address.zipCode = it
            }
        }

        return personRepository.saveAndFlush(person)
    }
}
