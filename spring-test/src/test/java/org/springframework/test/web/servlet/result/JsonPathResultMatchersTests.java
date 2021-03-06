/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.test.web.servlet.result;

import org.hamcrest.Matchers;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.StubMvcResult;

/**
 * Tests for {@link JsonPathResultMatchers}.
 *
 * @author Rossen Stoyanchev
 * @author Craig Andrews
 */
public class JsonPathResultMatchersTests {

	@Test
	public void value() throws Exception {
		new JsonPathResultMatchers("$.foo").value("bar").match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void valueNoMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").value("bogus").match(getStubMvcResult());
	}

	@Test
	public void valueMatcher() throws Exception {
		new JsonPathResultMatchers("$.foo").value(Matchers.equalTo("bar")).match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void valueMatcherNoMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").value(Matchers.equalTo("bogus")).match(getStubMvcResult());
	}

	@Test
	public void exists() throws Exception {
		new JsonPathResultMatchers("$.foo").exists().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void existsNoMatch() throws Exception {
		new JsonPathResultMatchers("$.bogus").exists().match(getStubMvcResult());
	}

	@Test
	public void doesNotExist() throws Exception {
		new JsonPathResultMatchers("$.bogus").doesNotExist().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void doesNotExistNoMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").doesNotExist().match(getStubMvcResult());
	}

	@Test
	public void isArrayMatch() throws Exception {
		new JsonPathResultMatchers("$.qux").isArray().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void isArrayNoMatch() throws Exception {
		new JsonPathResultMatchers("$.bar").isArray().match(getStubMvcResult());
	}

	@Test
	public void isBooleanMatch() throws Exception {
		new JsonPathResultMatchers("$.icanhaz").isBoolean().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void isBooleanNoMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").isBoolean().match(getStubMvcResult());
	}

	@Test
	public void isNumberMatch() throws Exception {
		new JsonPathResultMatchers("$.howmanies").isNumber().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void isNumberNoMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").isNumber().match(getStubMvcResult());
	}

	@Test
	public void isMapMatch() throws Exception {
		new JsonPathResultMatchers("$.cheeseburger").isMap().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void isMapNoMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").isMap().match(getStubMvcResult());
	}

	@Test
	public void isStringMatch() throws Exception {
		new JsonPathResultMatchers("$.foo").isString().match(getStubMvcResult());
	}

	@Test(expected = AssertionError.class)
	public void isStringNoMatch() throws Exception {
		new JsonPathResultMatchers("$.qux").isString().match(getStubMvcResult());
	}


	private static final String RESPONSE_CONTENT = "{\"foo\":\"bar\", \"qux\":[\"baz1\",\"baz2\"], \"icanhaz\":true, \"howmanies\": 5, \"cheeseburger\": {\"pickles\": true} }";


	private StubMvcResult getStubMvcResult() throws Exception {
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.addHeader("Content-Type", "application/json");
		response.getWriter().print(new String(RESPONSE_CONTENT.getBytes("ISO-8859-1")));
		return new StubMvcResult(null, null, null, null, null, null, response);
	}

}
