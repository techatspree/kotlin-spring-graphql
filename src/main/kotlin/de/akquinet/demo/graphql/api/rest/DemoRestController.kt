package de.akquinet.demo.graphql.api.rest

import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest")
class DemoRestController @Autowired constructor(val personRepository: PersonRepository) {

    @GetMapping("/persons")
    fun allPersons(): List<PersonEntity> =
            personRepository.findAll()

    @GetMapping("/person")
    fun personById(@RequestParam(value = "id") id: Long) =
            personRepository.findById(id)
}
