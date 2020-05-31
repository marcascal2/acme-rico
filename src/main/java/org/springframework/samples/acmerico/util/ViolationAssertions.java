package org.springframework.samples.acmerico.util;

import java.util.Objects;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.AbstractAssert;

public class ViolationAssertions extends AbstractAssert<ViolationAssertions, ConstraintViolation<?>> {

	private String message = "Expected character's name to be <%s> but was <%s>";
	
	public ViolationAssertions(ConstraintViolation<?> actual) {
	    super(actual, ViolationAssertions.class);
	}
	
	public static ViolationAssertions assertThat(ConstraintViolation<?> actual) {
	    return new ViolationAssertions(actual);
	}
	
	public ViolationAssertions throwsNotNullMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must not be null")) {
	      failWithMessage(message, "must not be null", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsNotBlankMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must not be blank")) {
	      failWithMessage(message, "must not be blank", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsNotEmptyMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must not be empty")) {
	      failWithMessage(message, "must not be empty", actual.getMessage());
	    }

	    return this;
	}
		
	public ViolationAssertions throwsPastDateMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must be a past date")) {
	      failWithMessage(message, "must be a past date", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsLengthAccountAliasMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "length must be between 0 and 30")) {
	      failWithMessage(message, "length must be between 0 and 30", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsInvalidAccountNumberMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Invalid account number")) {
	      failWithMessage(message, "Invalid account number", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsPatternStatusCCAppMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Card application status only can be ACCEPTED, REJECTED or PENDING")) {
	      failWithMessage(message, "Card application status only can be ACCEPTED, REJECTED or PENDING", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsPatternStatusTAppMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Transfer application status only can be ACCEPTED, REJECTED or PENDING")) {
	      failWithMessage(message, "Transfer application status only can be ACCEPTED, REJECTED or PENDING", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsPatternStatusLAppMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Loan application status only can be ACCEPTED, REJECTED or PENDING")) {
	      failWithMessage(message, "Loan application status only can be ACCEPTED, REJECTED or PENDING", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsMinimumTransferAmountMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "The amount must be greater than € 100, please if you need a smaller amount make an instant transfer")) {
	      failWithMessage(message, "The amount must be greater than € 100, please if you need a smaller amount make an instant transfer", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsInvalidCCNumberMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "invalid credit card number")) {
	      failWithMessage(message, "invalid credit card number", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsDeadlineCCMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Incorrect deadline")) {
	      failWithMessage(message, "Incorrect deadline", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsCVVMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Incorrect CVV")) {
	      failWithMessage(message, "Incorrect CVV", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsDebtAmountMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must be greater than or equal to 0")) {
	      failWithMessage(message, "must be greater than or equal to 0", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsRefreshDateDebtMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "Incorrect refresh date")) {
	      failWithMessage(message, "Incorrect refresh date", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsInstantTransferLoanMinimumAmountMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must be greater than or equal to 0.01")) {
	      failWithMessage(message, "must be greater than or equal to 0.01", actual.getMessage());
	    }

	    return this;
	}
	
	public ViolationAssertions throwsInstantTransferMaximumAmountMessage() {
	    isNotNull();

	    if (!Objects.equals(actual.getMessage(), "must be less than or equal to 99.99")) {
	      failWithMessage(message, "must be less than or equal to 99.99", actual.getMessage());
	    }

	    return this;
	}
}
