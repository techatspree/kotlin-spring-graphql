package de.akquinet.demo.graphql.service

import graphql.execution.NonNullableFieldWasNullException
import graphql.execution.ExecutionStrategyParameters
import graphql.execution.ExecutionContext
import graphql.ExecutionResult
import java.util.concurrent.CompletableFuture
import graphql.execution.AsyncExecutionStrategy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** see https://stackoverflow.com/a/48046402/6324593
 */
@Service
class AsyncTransactionalExecutionStrategyService : AsyncExecutionStrategy() {

    @Transactional
    @Throws(NonNullableFieldWasNullException::class)
    override fun execute(executionContext: ExecutionContext, parameters: ExecutionStrategyParameters): CompletableFuture<ExecutionResult> {
        return super.execute(executionContext, parameters)
    }
}
