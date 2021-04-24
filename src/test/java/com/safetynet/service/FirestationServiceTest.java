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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
    @Mock
    FirestationRepositoryImpl firestationRepository;
    @InjectMocks
    FirestationServiceImpl firestationService;

    private Firestation firestation, firestation1, firestation2, invalidIdFirestaiton, invalidAddressFirestation;
    private List<Firestation> firestations;

    @BeforeEach
    public void initTests(){
        firestation = new Firestation();
        firestation.setStation(1);
        firestation.setAddress("63 beaulieu");

        firestation1 = new Firestation();
        firestation1.setStation(2);
        firestation1.setAddress("mare 23 , porto");

        firestation2 = new Firestation();
        firestation2.setStation(3);
        firestation2.setAddress("Dassa, 78 joli");

        invalidIdFirestaiton = new Firestation();
        invalidIdFirestaiton.setStation(-1);
        invalidIdFirestaiton.setAddress("78 rue pal");

        invalidAddressFirestation = new Firestation();
        invalidAddressFirestation.setStation(2);
        invalidAddressFirestation.setAddress("");

        firestations = new ArrayList<>();
        firestations.add(firestation);
        firestations.add(firestation1);
        firestations.add(firestation2);

    }

    /**
     * given_a_valid_firestation_save_should_persist_the_person - test methode save: cas norminal
     * @throws FirestationInvalidException
     * @throws Exception
     */
    @Test
    public void given_a_valid_firestation_save_should_persist_the_person() throws FirestationInvalidException, Exception{
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

    @Test
    public void find_should_return_all_firestation() throws Exception{
        when(firestationRepository.findAll()).thenReturn(firestations);
        List<Firestation> allFirestations = firestationService.findAll();
        verify(firestationRepository).findAll();
        assertThat(allFirestations).hasSize(3);
    }

    @Test
    public void given_an_id_delete_should_remove_the_firestation() throws Exception{
        when(firestationRepository.findById(1)).thenReturn(firestation);
        //when(firestationRepository.delete(firestation)).thenReturn(anyList());
        assertThat(firestations).hasSize(3);
        firestationService.delete(1);
        verify(firestationRepository, times(1)).delete(any());
        //assertThat(firestations).hasSize(2);

    }

    @Test
    public void given_an_id_findbyid_should_return_firestation() throws Exception{
        when(firestationRepository.findById(1)).thenReturn(firestation);
        Firestation foundFirestation = firestationService.findById(1);
        assertThat(foundFirestation.getAddress()).isEqualTo("63 beaulieu");
    }

    @Test
    public void given_a_non_existing_firestation_id_find_should_raise_Exception() throws Exception{
        Assertions.assertThrows(FirestationNotFoundException.class, () -> {
            firestationService.findById(5);
        });
    }

    @Test
    public void given_an_address_findbyaddress_should_return_firestation() throws Exception{
        when(firestationRepository.findByAddress("Dassa, 78 joli")).thenReturn(firestation2);
        Firestation foundFirestation = firestationService.findByAddress("Dassa, 78 joli");
        assertThat(foundFirestation.getStation()).isEqualTo(3);
    }

    @Test
    public void given_a_non_existing_firestation_address_find_should_raise_Exception() throws Exception{
        Assertions.assertThrows(FirestationNotFoundException.class, () -> {
            firestationService.findByAddress("67 avenue clozel");
        });
    }


}
