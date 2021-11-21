package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger counter = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet, counter.getAndIncrement());
        return "Created successfully";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @PutMapping(value = "/updatePet", consumes = "application/json")
    public void updatePet(@RequestBody Map<String, String> data) {
        petModel.update(new Pet(data.get("name"), data.get("type"), Integer.parseInt(data.get("age"))), Integer.parseInt(data.get("id")));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public void deletePet(@RequestBody Map<String, Integer> id) {
        petModel.delete(id.get("id"));
    }
}
