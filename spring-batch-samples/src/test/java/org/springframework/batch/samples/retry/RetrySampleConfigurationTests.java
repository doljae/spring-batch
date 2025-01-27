/*
 * Copyright 2012-2022 the original author or authors.
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
package org.springframework.batch.samples.retry;

import org.junit.jupiter.api.Test;

import org.springframework.batch.core.Job;
import org.springframework.batch.samples.config.DataSourceConfiguration;
import org.springframework.batch.samples.config.JobRunnerConfiguration;
import org.springframework.batch.samples.domain.trade.internal.GeneratingTradeItemReader;
import org.springframework.batch.samples.support.RetrySampleItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Checks that expected number of items have been processed.
 *
 * @author Robert Kasanicky
 * @author Dave Syer
 * @author Mahmoud Ben Hassine
 * @author Glenn Renfro
 */
@SpringJUnitConfig(
		classes = { DataSourceConfiguration.class, RetrySampleConfiguration.class, JobRunnerConfiguration.class })
class RetrySampleConfigurationTests {

	@Autowired
	private GeneratingTradeItemReader itemGenerator;

	@Autowired
	private RetrySampleItemWriter<?> itemProcessor;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	void testLaunchJob(@Autowired Job job) throws Exception {
		this.jobLauncherTestUtils.setJob(job);
		this.jobLauncherTestUtils.launchJob();
		// items processed = items read + 2 exceptions
		assertEquals(itemGenerator.getLimit() + 2, itemProcessor.getCounter());
	}

}
