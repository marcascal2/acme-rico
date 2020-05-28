package org.springframework.samples.acmerico.util;

import java.util.Objects;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.AbstractAssert;

public class ViolationAssertions extends AbstractAssert<ViolationAssertions, ConstraintViolation<?>> {

	public ViolationAssertions(ConstraintViolation<?> actual) {
	    super(actual, ViolationAssertions.class);
	}
	
	public static ViolationAssertions assertThat(ConstraintViolation<?> actual) {
	    return new ViolationAssertions(actual);
	}
	
	public ViolationAssertions throwsNotNullMessage() {
	    // check that actual TolkienCharacter we want to make assertions on is not null.
	    isNotNull();

	    // check condition
	    if (!Objects.equals(actual.getMessage(), "must not be null")) {
	      failWithMessage("Expected character's name to be <%s> but was <%s>", "must not be null", actual.getMessage());
	    }

	    // return the current assertion for method chaining
	    return this;
	}
}
