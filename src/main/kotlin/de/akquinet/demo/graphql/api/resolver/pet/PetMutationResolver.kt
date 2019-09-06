package de.akquinet.demo.graphql.api.resolver.pet

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import de.akquinet.demo.graphql.model.PetEntity
import de.akquinet.demo.graphql.model.PetType
import de.akquinet.demo.graphql.repository.PersonRepository
import de.akquinet.demo.graphql.repository.PetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


data class PetCreateDto(
        val ownerId: Long,
        val name: String,
        val type: PetType)


data class PetUpdateDto(
        val ownerId: Long?,
        val name: String?,
        val type: PetType?)


@Component
class PetMutationResolver @Autowired constructor(private val personRepository: PersonRepository,
                                                 private val petRepository: PetRepository) : GraphQLMutationResolver {

    fun createPet(dto: PetCreateDto): PetEntity {
        val owner = personRepository.getOne(dto.ownerId)
        return petRepository.saveAndFlush(PetEntity(owner, dto.name, dto.type))
    }

    fun updatePet(petId: Long, dto: PetUpdateDto): PetEntity {
        val pet = petRepository.getOne(petId)

        dto.ownerId?.let {
            pet.owner = personRepository.getOne(it)
        }

        dto.name?.let {
            pet.name = it
        }

        dto.type?.let {
            pet.type = it
        }

        return petRepository.saveAndFlush(pet)
    }
}
