package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person person){

        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
    }
    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){

        return new ResponseEntity<>(personRepository.findOne(id),HttpStatus.OK);
    }
    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPersonList(){

        return new ResponseEntity<>((List<Person>) personRepository.findAll(),HttpStatus.OK);
    }
    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person){
        if(personRepository.findOne(id) != null){
            Person originalPerson = personRepository.findOne(id);
            originalPerson.setFirstName(person.getFirstName());
            originalPerson.setLastName(person.getLastName());
            return new ResponseEntity<>(personRepository.save(originalPerson),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(personRepository.save(person),HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable Long id){
        boolean isDeleted = false;
        if(personRepository.findOne(id) != null) {
            personRepository.delete(id);
            isDeleted = true;
        }
        return new ResponseEntity<>(isDeleted,HttpStatus.OK);
    }
}
