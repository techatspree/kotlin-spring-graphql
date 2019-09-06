package de.akquinet.demo.graphql.repository

import de.akquinet.demo.graphql.AbstractDbTest
import de.akquinet.demo.graphql.model.AddressEntity
import de.akquinet.demo.graphql.model.PersonEntity
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.assertj.core.api.Assertions.*

class PersonRepositoryTest : AbstractDbTest() {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Test
    fun letsTestDB() {
        val saveAndFlush = personRepository.saveAndFlush(PersonEntity("n", AddressEntity("s", "z", "c")))
        assertThat(saveAndFlush.name).isEqualTo("n")
        assertThat(personRepository.findAll()).hasSize(1)
    }
}
