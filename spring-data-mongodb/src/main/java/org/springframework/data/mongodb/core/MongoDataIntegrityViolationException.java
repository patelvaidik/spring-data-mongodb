/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.core;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.mongodb.WriteResult;

/**
 * Mongo-specific {@link DataIntegrityViolationException}.
 *
 * @author Oliver Gierke
 */
public class MongoDataIntegrityViolationException extends DataIntegrityViolationException {

	private static final long serialVersionUID = -186980521176764046L;

	private final WriteResult writeResult;
	private final MongoActionOperation actionOperation;

	/**
	 * Creates a new {@link MongoDataIntegrityViolationException} using the given message and {@link WriteResult}.
	 *
	 * @param message the exception message
	 * @param writeResult the {@link WriteResult} that causes the exception, must not be {@literal null}.
	 * @param actionOperation the {@link MongoActionOperation} that caused the exception, must not be {@literal null}.
	 */
	public MongoDataIntegrityViolationException(String message, WriteResult writeResult,
												MongoActionOperation actionOperation) {

		super(message);

		Assert.notNull(writeResult, "WriteResult must not be null!");
		Assert.notNull(actionOperation, "MongoActionOperation must not be null!");

		this.writeResult = writeResult;
		this.actionOperation = actionOperation;
	}

	/**
	 * Returns the {@link WriteResult} that caused the exception.
	 *
	 * @return the writeResult
	 */
	public WriteResult getWriteResult() {
		return writeResult;
	}

	/**
	 * Returns the {@link MongoActionOperation} in which the current exception occured.
	 *
	 * @return the actionOperation
	 */
	public MongoActionOperation getActionOperation() {
		return actionOperation;
	}
}
