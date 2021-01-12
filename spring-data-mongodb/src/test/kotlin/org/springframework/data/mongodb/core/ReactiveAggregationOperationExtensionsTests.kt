/*
 * Copyright 2017-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.core

import example.first.First
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import reactor.core.publisher.Flux

/**
 * @author Mark Paluch
 * @author Sebastien Deleuze
 */
class ReactiveAggregationOperationExtensionsTests {

	val operation = mockk<ReactiveAggregationOperation>(relaxed = true)

	@Test // DATAMONGO-1719
	@Suppress("DEPRECATION")
	fun `aggregateAndReturn(KClass) extension should call its Java counterpart`() {

		operation.aggregateAndReturn(First::class)
		verify { operation.aggregateAndReturn(First::class.java) }
	}

	@Test // DATAMONGO-1719
	fun `aggregateAndReturn() with reified type parameter extension should call its Java counterpart`() {

		operation.aggregateAndReturn<First>()
		verify { operation.aggregateAndReturn(First::class.java) }
	}

	@Test // DATAMONGO-2255
	fun terminatingAggregationOperationAllAsFlow() {

		val spec = mockk<ReactiveAggregationOperation.TerminatingAggregationOperation<String>>()
		every { spec.all() } returns Flux.just("foo", "bar", "baz")

		runBlocking {
			assertThat(spec.flow().toList()).contains("foo", "bar", "baz")
		}

		verify {
			spec.all()
		}
	}

}
