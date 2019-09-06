package de.akquinet.demo.graphql.service

import de.akquinet.demo.graphql.model.AddressEntity
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.model.PetEntity
import de.akquinet.demo.graphql.model.PetType
import de.akquinet.demo.graphql.repository.PersonRepository
import de.akquinet.demo.graphql.repository.PetRepository
import de.akquinet.demo.graphql.utils.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BootstrapService @Autowired constructor(private val personRepository: PersonRepository,
                                              private val petRepository: PetRepository) : ApplicationListener<ApplicationReadyEvent> {

    private val logger = logger()

    @Value("\${application.testdata:false}")
    private val testdata: Boolean = false

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        if (testdata) {
            logger.info("Start inserting of test data")
            addTestdata()
            logger.info("Finished inserting of test data")
        } else {
            logger.info("Skip inserting test data")
        }
    }

    private fun addTestdata() {
        val p0 = personRepository.saveAndFlush(
                PersonEntity("Alice",
                        AddressEntity("Alices street", "123", "Berlin")
                ))
        petRepository.saveAndFlush(PetEntity(p0, "Bello", PetType.DOG))
        petRepository.saveAndFlush(PetEntity(p0, "Tiger", PetType.CAT))


        val p1 = personRepository.saveAndFlush(
                PersonEntity("other name",
                        AddressEntity("other street", "123", "Berlin")
                ))
        petRepository.saveAndFlush(PetEntity(p1, "Tweety", PetType.BIRD))
    }
}
