package HU2.HU2.Service;

import HU2.HU2.Entity.ClientEntity;
import HU2.HU2.Model.LoanRequest;
import HU2.HU2.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    RestTemplate restTemplate;

    // P2: Creates a new client and saves it in the repository
    public ClientEntity createClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    // Retrieves a client from the repository by its ID
    public ClientEntity getClientById(long id) {
        return clientRepository.findById(id);
    }

    /**
     * Retrieves a client entity from the database using the RUT.
     *
     * @param rut The RUT of the client to be retrieved.
     * @return The ClientEntity object if found, otherwise null.
     */
    public ClientEntity getClient(String rut) {
        return clientRepository.findByRut(rut);
    }

    /**
     * P6: Calculates the total cost of a loan including insurance and administration fees.
     *
     * @param amount              The loan amount.
     * @param termYears           The loan term in years.
     * @param annualInterest      The annual interest rate.
     * @param lifeInsurance       The life insurance cost as a percentage of the loan amount.
     * @param fireInsurance       The fixed cost of fire insurance.
     * @param adminFee            The administration fee as a percentage of the loan amount.
     * @return The total loan cost over the entire term.
     */
    public int totalCostP6(int amount, int termYears, double annualInterest, double lifeInsurance, double fireInsurance, double adminFee) {
        // Calculate the total term in months
        int termMonths = termYears * 12;

        // Create LoanRequest object
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setAmount(amount);
        loanRequest.setTermYears(termYears);
        loanRequest.setAnnualInterest(annualInterest);

        // Call microservice to calculate the monthly loan payment
        double monthlyLoan = getMonthlyLoan(loanRequest);

        // Calculate the life insurance cost (percentage of the loan amount)
        double lifeInsuranceCost = lifeInsurance * amount;

        // Calculate the administration fee
        double fee = adminFee * amount;

        // Total monthly cost including loan payment, fire insurance, and life insurance
        double totalMonthlyCost = monthlyLoan + fireInsurance + lifeInsuranceCost;

        // Return the total cost over the entire loan term plus the administration fee
        double total = (totalMonthlyCost * termMonths) + fee;

        return (int) total;
    }

    private double getMonthlyLoan(LoanRequest loanRequest) {
        String url = "http://loan-service/HU1/simulateLoanAmount";
        HttpEntity<LoanRequest> request = new HttpEntity<>(loanRequest);
        ResponseEntity<Double> response = restTemplate.postForEntity(url, request, Double.class);
        return response.getBody();
    }

    /**
     * R1: Determines if a client is eligible for a loan based on their salary and the loan amount.
     *
     * @param Id The RUT of the client.
     * @param amount The loan amount.
     * @param termYears The loan term in years.
     * @param annualInterest The annual interest rate.
     * @return true if the loan amount does not exceed 35% of the client's salary.
     */
    public boolean R1(long Id, int amount, int termYears, double annualInterest) {
        // Create LoanRequest object
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setAmount(amount);
        loanRequest.setTermYears(termYears);
        loanRequest.setAnnualInterest(annualInterest);

        // Call microservice to calculate the monthly loan payment
        double M = getMonthlyLoan(loanRequest);

        // Retrieve client by RUT
        ClientEntity client = clientRepository.findById(Id);
        // Get client's salary
        int salary = client.getSalary();

        // Calculate the loan-to-salary percentage
        double result = (M / salary) * 100;

        // Return true if the loan amount exceeds 35% of the salary
        return !(result < 35);
    }

    /**
     * R2: Checks if a client has a bad credit record (Dicom).
     *
     * @param clientId The ID of the client.
     * @return true if the client has a bad credit record, false otherwise.
     */
    public boolean R2(long clientId) {
        // Retrieve client by ID
        ClientEntity client = clientRepository.findById(clientId);
        // Check if the client has a bad credit record (Dicom)
        boolean dicom = client.GetDicom();
        return dicom;
    }

    /**
     * R3: Checks if a client has been employed for more than one year.
     *
     * @param clientId The ID of the client.
     * @return true if the client has more than one year of employment, false otherwise.
     */
    public boolean R3(long clientId) {
        // Retrieve client by ID
        ClientEntity client = clientRepository.findById(clientId);
        // Check the client's job tenure in years
        int jobTenure = client.getJobTenure();
        return jobTenure > 1;
    }

    /**
     * R4: Determines if the debt-to-income ratio is acceptable.
     *
     * @param clientId The ID of the client.
     * @param debt     The total debt of the client.
     * @param amount   The loan amount.
     * @return true if the debt-to-income ratio is less than 50%.
     */
    public boolean R4(long clientId, int debt, int amount) {
        // Calculate the total debt after adding the loan
        int sum = amount + debt;
        // Retrieve client by ID
        ClientEntity client = clientRepository.findById(clientId);
        // Get the client's salary
        int salary = client.getSalary();

        // Calculate the debt-to-income ratio
        double ratio = salary / sum;

        // Return true if the ratio is acceptable (greater than 50%)
        return ratio >= 0.5;
    }

    /**
     * R5: Determines if the loan amount is appropriate based on the property type.
     *
     * @param type    The property type (1 = first house, 2 = second house, etc.).
     * @param cost    The cost of the property.
     * @param loan    The loan amount.
     * @return true if the loan covers a sufficient percentage of the cost for the property type.
     */
    public boolean R5(int type, int cost, int loan) {
        double maxLoan;
        // Type 1: First house (80% loan coverage)
        if (type == 1) {
            maxLoan = loan * 0.8;
            return maxLoan < cost;
        }
        // Type 2: Second house (70% loan coverage)
        if (type == 2) {
            maxLoan = loan * 0.7;
            return maxLoan < cost;
        }
        // Type 3: Commercial property (60% loan coverage)
        if (type == 3) {
            maxLoan = loan * 0.6;
            return maxLoan < cost;
        }
        // Type 4: Remodeling (50% loan coverage)
        if (type == 4) {
            maxLoan = loan * 0.5;
            return maxLoan < cost;
        }
        return false;
    }

    /**
     * R6: Checks if the client's age is below 70.
     *
     * @param id The ID of the client.
     * @return true if the client is younger than 70, false otherwise.
     */
    public boolean R6(long id) {
        // Retrieve client by ID
        ClientEntity client = clientRepository.findById(id);
        // Get client's age
        int age = client.getAge();
        return age <= 70;
    }
}
