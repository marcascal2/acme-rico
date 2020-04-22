package org.springframework.samples.acmerico.util;

import java.util.Random;

public class BankAccountNumberGenerator {

    public String generateRandomNumber() {
        String accountNumber = "ES";
        Random value = new Random();

        int r1 = value.nextInt(10);
        int r2 = value.nextInt(10);
        accountNumber += Integer.toString(r1) + Integer.toString(r2) + " ";

        int count = 0;
        int n = 0;
        for(int i =0; i < 16;i++)
        {
            if(count == 4)
            {
                accountNumber += " ";
                count =0;
            }
            else 
                n = value.nextInt(10);
                accountNumber += Integer.toString(n);
                count++;            

        }
        return accountNumber;
    }
}