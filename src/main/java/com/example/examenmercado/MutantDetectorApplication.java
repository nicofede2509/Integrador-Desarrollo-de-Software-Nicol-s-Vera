package com.example.examenmercado;

import com.example.examenmercado.service.MutantDetector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantDetectorApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(MutantDetectorApplication.class, args);
	}
    @Override
    public void run(String... args) {
        MutantDetector detector = new MutantDetector();
        boolean rdo = detector.isMutant(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        if(rdo){
            System.out.println("Mutante");
        }else{
            System.out.println("No mutante");
        }
    }
}
