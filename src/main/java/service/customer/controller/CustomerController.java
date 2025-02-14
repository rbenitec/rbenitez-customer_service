package service.customer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.customer.entity.Customer;
import service.customer.model.CustomerDto;
import service.customer.service.CustomerService;

//@RestController
//@RequestMapping("/customer")
//@RequiredArgsConstructor
public class CustomerController {

    //uri: "mongodb+srv://rbenitec:rjbc2095@cluster0.zaixl.mongodb.net/MongoPractica?retryWrites=true&w=majority"
/*
    private final CustomerService iclientService;


    @GetMapping("/all")   // Mostrar todos los clientes
    public Flux<Customer> getClient() {

        return iclientService.findAll();
    }

    @GetMapping("/getClientByAge/{dni}")  //--> Encontrar cliente por dni
    public Flux<Customer> getClientByAge(@PathVariable("dni") String dni) {

        return iclientService.findClientByAge(dni);
    }

    @PostMapping()
        //--> Ingresar datos del cliente
    Mono<Customer> postClient(@RequestBody Customer customerDto) {
        return iclientService.save(customerDto);
    }

    /*
    @PostMapping("/updateClient")
        //--> Actualizar datos del cliente
    Mono<CustomerDto> updClient(@RequestBody Customer customerDto) {
        return iclientService.update(customerDto);
    }

    /*
    @GetMapping("/deleteClient/{id}")
    Mono<Void> deleteClient(@PathVariable("id") String id) {
        return iclientService.delete(id);
    }


    /*
    @PostMapping("/deleteClient")
    Flux<CustomerDto> deleteClient(@PathVariable("id") String id){
        return iclientService.delete(id);
    }

     */
}
