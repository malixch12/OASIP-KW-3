package sit.int204.classicmodelsservice2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice2.entities.Office;
import sit.int204.classicmodelsservice2.repositories.OfficeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    @Autowired
    private OfficeRepository repository;

    @GetMapping("")
    public List<Office> getAllOffice(){
        return repository.findAll();
    }

    @GetMapping("/{officeCode}")
    public Office getOffice(@PathVariable String officeCode) {
        return repository.findById(officeCode).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,officeCode+ " does not exist !!!"));
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Office create(@RequestBody Office newOffice) {

        return repository.saveAndFlush(newOffice);

    }

    @DeleteMapping("/{officeCode}")

    public void delete(@PathVariable String officeCode) {
        repository.findById(officeCode).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, officeCode + " does not exist"));
        repository.deleteById(officeCode);
    }

    private Office mapOffice(Office existingOffice, Office updateOffice) {
        existingOffice.setAddressLine1(updateOffice.getAddressLine1());
        existingOffice.setAddressLine2(updateOffice.getAddressLine2());
        existingOffice.setCity(updateOffice.getCity());
        existingOffice.setCountry(updateOffice.getCountry());
        existingOffice.setPhone(updateOffice.getPhone());
        existingOffice.setPostalCode(updateOffice.getPostalCode());
        existingOffice.setState(updateOffice.getState());
        existingOffice.setTerritory(updateOffice.getTerritory());
        return existingOffice; }

    @PutMapping("/{officeCode}")
    public Office update(@RequestBody Office updateOffice, @PathVariable String officeCode) {
        Office office = repository.findById(officeCode).map(o -> mapOffice(o, updateOffice)).orElseGet(() -> {
            updateOffice.setId(officeCode);
            return updateOffice;
        });
        return repository.saveAndFlush(office);
    }

}
