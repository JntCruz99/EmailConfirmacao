package br.com.jntcruz.EmailConfirmacao.controller;

import br.com.jntcruz.EmailConfirmacao.entity.User;
import br.com.jntcruz.EmailConfirmacao.repository.UserRepository;
import br.com.jntcruz.EmailConfirmacao.service.ActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class Controller {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivationService activationService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createdUsers(@RequestBody User user){

        user.setActived(false);
        user.setActivationCode(activationService.generateActivationCode());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Favor ativar sua conta com o codigo: " + user.getActivationCode());
    }

    @PostMapping("/activation/{code}")
    public ResponseEntity<?> activationEmail(@PathVariable String code){
        Optional<User> userOptional = userRepository.findByActivationCode(code);
        if (userOptional.isPresent()){

            User user = userOptional.get();
            user.setActived(true);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("EMAIL ATIVADO!!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CODIGO IVALIDO");
        }
    }

}
