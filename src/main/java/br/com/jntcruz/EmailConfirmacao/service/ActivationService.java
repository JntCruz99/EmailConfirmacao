package br.com.jntcruz.EmailConfirmacao.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ActivationService {

    public String generateActivationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Gerar um código de 4 dígitos
        return String.valueOf(code);
    }
}
