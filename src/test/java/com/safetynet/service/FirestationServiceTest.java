package com.safetynet.service;

import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
    @Mock
    FirestationRepositoryImpl firestationRepository;
    @InjectMocks
    FirestationServiceImpl firestationService;

    private Firestation firestation, firestation1, firestation2,firestation3,
            invalidIdFirestaiton, invalidAddressFirestation, nonExistingFirestation;
    private List<Firestation> firestations, firestationsAtAddress;
    private Optional<Firestation> notFoundFirestation;
    private Optional<List<Firestation>> foundFirestation;

    @BeforeEach
    public void initTests(){
        firestation = new Firestation();
        firestation.setId(1);
        firestation.setStation(1);
        firestation.setAddress("63 beaulieu");

        firestation1 = new Firestation();
        firestation1.setId(2);
        firestation1.setStation(2);
        firestation1.setAddress("mare 23 , porto");

        firestation2 = new Firestation();
        firestation2.setId(3);
        firestation2.setStation(3);
        firestation2.setAddress("Dassa, 78 joli");

        firestation3 = new Firestation();
        firestation3.setId(4);
        firestation3.setStation(4);
        firestation3.setAddress("Dassa, 78 joli");

        invalidIdFirestaiton = new Firestation();
        invalidIdFirestaiton.setId(-1);
        invalidIdFirestaiton.setStation(-1);
        invalidIdFirestaiton.setAddress("78 rue pal");

        invalidAddressFirestation = new Firestation();
        invalidAddressFirestation.setId(2);
        invalidAddressFirestation.setStation(2);
        invalidAddressFirestation.setAddress("");

        nonExistingFirestation = new Firestation();
        nonExistingFirestation.setId(3);
        nonExistingFirestation.setStation(3);
        nonExistingFirestation.setAddress("34 rue UAC");

        firestations = new ArrayList<>();
        firestations.add(firestation);
        firestations.add(firestation1);
        firestations.add(firestation2);

        firestationsAtAddress = new ArrayList<>();
        firestationsAtAddress.add(firestation2);
        foundFirestation = Optional.of(firestationsAtAddress);
        notFoundFirestation = Optional.empty();

    }

    /**
     * given_a_valid_firestation_save_should_persist_the_person - test methode save: cas norminal
     * @throws FirestationInvalidException
     * @throws Exception
     */
    @Test
    public void given_a_valid_firestation_save_should_persist_the_firestation() throws FirestationInvalidException, Exception{
        when(firestationRepository.save(any(Firestation.class))).thenReturn(firestation);
        Firestation savedFiresation = firestationService.save(firestation);
        verify(firestationRepository).save(any(Firestation.class));
        assertThat(savedFiresation).isNotNull();
    }

    /**
     * given_an_invalid_firestation_id_save_should_raise_exception - scenario alternatif
     * @throws Exception
     */
    @Test
    public void given_an_invalid_firestation_id_save_should_raise_exception() throws Exception {
        Assertions.assertThrows(FirestationInvalidException.class, () ->{
            firestationService.save(invalidIdFirestaiton);
        });
    }

    /**
     * given_an_invalid_firestation_address_save_should_raise_exception - scenario alternatif
     * @throws Exception
     */
    @Test
    public void given_an_invalid_firestation_address_save_should_raise_exception() throws Exception {
        Assertions.assertThrows(FirestationInvalidException.class, () ->{
            firestationService.save(invalidAddressFirestation);
        });
    }

    /**
     * given_a_valid_firestation_update_should_save_changes - update: scenario nominal
     * @throws Exception
     */
    @Test
    public void given_a_valid_firestation_update_should_save_changes() throws Exception{
        when(firestationRepository.findById(anyInt())).thenReturn(Optional.of(firestation));
        when(firestationRepository.save(any())).thenReturn(firestation);
        Firestation updatedFirestation = firestationService.update(firestation);
        verify(firestationRepository).save(any(Firestation.class));
        assertThat(updatedFirestation).isNotNull();
        //Test that updated fields have really changed
        assertThat(updatedFirestation.getAddress()).isSameAs(firestation.getAddress());
    }

    /**
     * given_an_invalid_firestation_id_update_should_raise_exception - scenario alternatif
     * @throws Exception
     */
    @Test
    public void given_an_invalid_firestation_id_update_should_raise_exception() throws Exception {
        Assertions.assertThrows(FirestationInvalidException.class, () ->{
            firestationService.update(invalidIdFirestaiton);
        });
    }

    /**
     * given_an_invalid_firestation_address_update_should_raise_exception - scenario alternatif
     * @throws Exception
     */
    @Test
    public void given_an_invalid_firestation_address_update_should_raise_exception() throws Exception {
        Assertions.assertThrows(FirestationInvalidException.class, () ->{
            firestationService.update(invalidAddressFirestation);
        });
    }

    @Test
    public void given_a_non_existing_firestation_udpate_should_raise_exception() throws Exception{
        when(firestationRepository.findById(3)).thenReturn(notFoundFirestation);
        Assertions.assertThrows(FirestationNotFoundException.class, ()->{
            firestationService.update(nonExistingFirestation);
        });
    }

    @Test
    public void find_should_return_all_firestation() throws Exception{
        when(firestationRepository.findAll()).thenReturn(firestations);
        List<Firestation> allFirestations = firestationService.findAll();
        verify(firestationRepository).findAll();
        assertThat(allFirestations).hasSize(3);
    }

    @Test
    public void given_an_id_delete_should_remove_the_firestation() throws Exception{
        when(firestationRepository.findById(1)).thenReturn(Optional.of(firestation));
        //when(firestationRepository.delete(firestation)).thenReturn(anyList());
        assertThat(firestations).hasSize(3);
        firestationService.delete(1);
        verify(firestationRepository, times(1)).delete(any());
        //assertThat(firestations).hasSize(2);

    }

    @Test
    public void given_a_non_existing_firestation_id_delete_should_raise_Exception() throws Exception{
        when(firestationRepository.findById(5)).thenReturn(notFoundFirestation);
        Assertions.assertThrows(FirestationNotFoundException.class, () -> {
            firestationService.delete(5);
        });
    }

    @Test
    public void given_an_id_findbyid_should_return_firestation() throws Exception{
        when(firestationRepository.findById(1)).thenReturn(Optional.of(firestation));
        Firestation foundFirestation = firestationService.findById(1);
        assertThat(foundFirestation.getAddress()).isEqualTo("63 beaulieu");
    }

    @Test
    public void given_a_non_existing_firestation_id_find_should_raise_Exception() throws Exception{
        Assertions.assertThrows(FirestationNotFoundException.class, () -> {
            firestationService.findByStation(5);
        });
    }

    @Test
    public void given_an_address_findbyaddress_should_return_firestation() throws Exception{
        when(firestationRepository.findByAddress("Dassa, 78 joli")).thenReturn(foundFirestation);
        List<Firestation> foundFirestation = firestationService.findByAddress("Dassa, 78 joli");
        assertThat(foundFirestation.get(0).getStation()).isEqualTo(3);
    }

    @Test
    public void given_a_non_existing_firestation_address_find_should_raise_Exception() throws Exception{
        Assertions.assertThrows(FirestationNotFoundException.class, () -> {
            firestationService.findByAddress("67 avenue clozel");
        });
    }


}
