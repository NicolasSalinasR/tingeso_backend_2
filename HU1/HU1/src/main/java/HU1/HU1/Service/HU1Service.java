package HU1.HU1.Service;

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
    public static int simulateLoanAmount(int amount, int termYears, double annualInterest) {
        // Monthly interest rate
        double monthlyRate = annualInterest / 12;
        // Total number of months for the loan term
        int totalMonths = termYears * 12;
        // Calculate the monthly rate + 1 for the amortization formula
        float monthlyRatePlusOne = (float) (monthlyRate + 1);

        System.out.println("yo deberia ser 0.00375 " + monthlyRate);
        System.out.println("yo deberia ser 240 " + totalMonths);

        // Power term for the amortization formula
        float powerTerm = (float) Math.pow(monthlyRatePlusOne, totalMonths);




        // Numerator of the formula
        double numerator = (monthlyRate * powerTerm);
        System.out.println("yo deberia ser: 0.0092079989" +numerator);

        // Denominator of the formula
        double denominator = powerTerm - 1;



        // Return the calculated monthly payment amount
        double D =   (numerator / denominator);

        System.out.println("yo deberia ser: 0.0063264938 " + D);

        double N = amount * D;
        int J = (int) N;
        return J;
    }
}
