package sg.nus.iss.javaspring.coins.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import sg.nus.iss.javaspring.coins.validator.ValidDenomination;

import java.util.List;

@Setter
@Getter

public class AmountRequest {
    @NotNull(message="please enter amount")
    @DecimalMax(value="10000.00",inclusive = true, message = "amount shouldn't be more than 10000.00")
    @DecimalMin(value = "0.00", inclusive = true, message = "amount shouldn't be less than 0")
    @Digits(integer = 5,fraction = 2,message = "amount must have up to 5 integer digits and 2 decimal places")
    private Double amount;
    @NotNull(message = "please enter denominations")
    @NotEmpty(message = "please enter denominations")
    @Size(min=1,message = "At least one denomination must be provided")
    @ValidDenomination
    private List<Double> denominations;
    public AmountRequest() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Double> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Double> denominations) {
        this.denominations = denominations;
    }
}
