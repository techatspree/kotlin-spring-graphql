package de.akquinet.demo.graphql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class GraphqlApplication

fun main(args: Array<String>) {
	runApplication<GraphqlApplication>(*args)
}
