package com.netshiftdigital.dhhpodcast.config;

import com.netshiftdigital.dhhpodcast.exceptions.CustomInternalServerException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class Vehicle {
    public static void main(String[] args) {
        try {
            int x = 10;
            if(x > 10){
                System.out.println("x is greater than 10");
            }
            throw new ArithmeticException("error");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
