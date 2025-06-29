package sg.nus.iss.javaspring.coins;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.util.Arrays;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sg.nus.iss.javaspring.coins.DTO.AmountRequest;

import java.util.Set;

@SpringBootTest
class CoinsApplicationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidAmountRequest() {
        AmountRequest ar = new AmountRequest();
        ar.setAmount(100.0);
        ar.setDenominations(Arrays.asList(0.01, 0.05, 1.0, 5.0));

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(ar);
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidAmountRequest() {
        AmountRequest ar = new AmountRequest();
        ar.setAmount(-1.0);  // invalid amount < 0
        ar.setDenominations(Arrays.asList(0.03, 0.07)); // invalid denominations if validated

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(ar);
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void validAmountRequest_ShouldPassValidation() {
        AmountRequest req = new AmountRequest();
        req.setAmount(9999.99);
        req.setDenominations(Arrays.asList(0.01, 0.05, 0.1, 5.0, 10.0));

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid request");
    }

    @Test
    public void nullAmount_ShouldFailValidation() {
        AmountRequest req = new AmountRequest();
        req.setAmount(null);  // Null amount, should trigger @NotNull
        req.setDenominations(Arrays.asList(1.0));

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty(), "Null amount should fail validation");
    }

    @Test
    public void negativeAmount_ShouldFailValidation() {
        AmountRequest req = new AmountRequest();
        req.setAmount(-10.0);
        req.setDenominations(Arrays.asList(1.0));

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty(), "Negative amount should fail validation");

        boolean hasNegativeAmountMessage = violations.stream()
                .anyMatch(v -> v.getMessage().toLowerCase().contains("less than 0") || v.getMessage().toLowerCase().contains("negative"));
        assertTrue(hasNegativeAmountMessage);
    }

    @Test
    public void denominationsEmpty_ShouldFailValidation() {
        AmountRequest req = new AmountRequest();
        req.setAmount(10.0);
        req.setDenominations(Arrays.asList());

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty(), "Empty denominations list should fail validation");

        boolean hasEmptyDenominationsMessage = violations.stream()
                .anyMatch(v -> v.getMessage().contains("At least one denomination"));
        assertTrue(hasEmptyDenominationsMessage);
    }

    @Test
    public void invalidDenomination_ShouldFailValidation() {
        AmountRequest req = new AmountRequest();
        req.setAmount(10.0);
        req.setDenominations(Arrays.asList(0.03));  // Invalid denomination

        Set<ConstraintViolation<AmountRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty(), "Invalid denomination should fail validation");

        boolean hasInvalidDenominationMessage = violations.stream()
                .anyMatch(v -> v.getMessage().contains("Denominations list contains invalid coin values"));
        assertTrue(hasInvalidDenominationMessage);
    }

}
