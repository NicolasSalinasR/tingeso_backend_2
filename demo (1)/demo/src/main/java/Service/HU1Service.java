package Service;

import org.springframework.stereotype.Service;

@Service
public class HU1Service {

    /**
     * P1
     * Calculates the monthly payment (installment) for a simulated loan using the formula for an amortizing loan.
     *
     * @param amount        The loan amount.
     * @param termYears     The term of the loan in years.
     * @param annualInterest The annual interest rate as a decimal (e.g., 0.05 for 5%).
     * @return The monthly payment amount.
     */
    public int simulateLoanAmount(int amount, int termYears, double annualInterest) {
        double monthlyRate = annualInterest / 12;
        int totalMonths = termYears * 12;
        float monthlyRatePlusOne = (float) (monthlyRate + 1);

        System.out.println("yo deberia ser 0.00375 " + monthlyRate);
        System.out.println("yo deberia ser 240 " + totalMonths);

        float powerTerm = (float) Math.pow(monthlyRatePlusOne, totalMonths);
        double numerator = (monthlyRate * powerTerm);
        System.out.println("yo deberia ser: 0.0092079989" + numerator);

        double denominator = powerTerm - 1;
        double D = (numerator / denominator);

        System.out.println("yo deberia ser: 0.0063264938 " + D);
        double N = amount * D;
        return (int) N;
    }
}
