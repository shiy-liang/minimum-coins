package sg.nus.iss.javaspring.coins.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.nus.iss.javaspring.coins.DTO.AmountRequest;
import sg.nus.iss.javaspring.coins.DTO.CoinsCount;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GeneralRestController {
    List<Double> denominations = new ArrayList<>();
    double amount = 0;
    @PostMapping("/input")
    public ResponseEntity<?> input(@Valid @RequestBody AmountRequest amountRequest) {
        this.amount = amountRequest.getAmount();
        this.denominations.clear();
        this.denominations.addAll(amountRequest.getDenominations());
        denominations.sort(Comparator.reverseOrder());

        double remaining = amount;
        for (double d : denominations) {
            int count = (int)(remaining / d);
            remaining -= count * d;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        if (Math.abs(remaining) > 1e-6) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Amount cannot be fully divided by denominations"));
        }
        return new ResponseEntity<>(amount + " " + denominations.toString(), HttpStatus.OK);
    }
    @GetMapping("/calculator")
    public ResponseEntity<List<CoinsCount>> calculator() {
        System.out.println("amount = " + amount);
        System.out.println("denominations = " + denominations);
        List<CoinsCount> result = new ArrayList<>();
        int sum = (int) Math.round(amount * 100);
        for(Double d : denominations){
            int de= (int) Math.round(d * 100);
            CoinsCount coinsCount = new CoinsCount();
            int num = sum / de;
            coinsCount.setCount(num);
            coinsCount.setDenomination(d);
            result.add(coinsCount);
            sum -= num*de;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

