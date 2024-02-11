package com.dxpai.loan;


import com.dxpai.model.Eligible;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Loan", description = "Check Loan Eligibility")
@RestController
public class EligibilityChecker {

    @Operation(
            summary = "Loan Eligibility",
            description = "Check if you are eligible for a loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @RequestMapping(value = "/loan", method = RequestMethod.GET)
    public Eligible checkLoanEligibility(int age, double income, double housingExpenses, double foodExpenses, int creditScore) {
        double availableIncome = income - (housingExpenses + foodExpenses);
        if (age >= 21 && age <= 60 && income >= 2000 && availableIncome > 0 && creditScore > 650) {
            Eligible eligible = new Eligible(availableIncome/2,true);
            eligible.getActions().put("Insurance","Buy your Travel Insurance");
            return eligible;
        } else {
            Eligible eligible = new Eligible(0,false);
            eligible.getActions().put("POT","Create a Wish POT and start saving for your trip");
            eligible.getActions().put("Insurance","Buy your Travel Insurance");
            return eligible;
        }
    }
}
