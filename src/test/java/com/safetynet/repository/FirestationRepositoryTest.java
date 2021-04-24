package com.safetynet.repository;

import com.safetynet.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FirestationRepositoryTest {
    @Mock
    DataSourceComponent dataSourceComponent;
    @InjectMocks
    private FirestationRepositoryImpl firestationRepository;
    protected Firestation firestation, firestation1, firestation2;
    List<Firestation> firestations;


    @BeforeEach
    public void initTests(){
        firestation = new Firestation();
        firestation.setStation(1);
        firestation.setAddress("43 rue masson");

        firestation1 = new Firestation();
        firestation1.setStation(2);
        firestation1.setAddress("23 rue behanzin");

        firestation2 = new Firestation();
        firestation2.setStation(3);
        firestation2.setAddress("53 rue soglo");

        firestations = new ArrayList<>();
        firestations.add(firestation);
        firestations.add(firestation1);
        firestations.add(firestation2);
    }

    @Test
    public void findAll_should_return_all_firestation() throws  Exception{
        when(dataSourceComponent.getFirestations()).thenReturn(firestations);
        List<Firestation> allFirestation = firestationRepository.findAll();
        assertThat(allFirestation).hasSize(3);
    }

    @Test
    public void given_a_new_firestation_save_should_persist_the_firestation() throws  Exception{
        Firestation newFirestation = new Firestation();
        newFirestation.setStation(4);
        newFirestation.setAddress("4 Avenue Toffa");
        when(dataSourceComponent.getFirestations()).thenReturn(firestations);
        Firestation savedFirestation = firestationRepository.save(newFirestation);
        assertThat(firestations).hasSize(4);
        assertThat(savedFirestation.getAddress()).isSameAs(firestations.get(3).getAddress());
    }

    @Test
    public void given_an_existing_firestation_save_should_update_the_firestation() throws Exception{
        Firestation newFirestation = new Firestation();
        newFirestation.setStation(2);
        newFirestation.setAddress("034 rue Talon");
        when(dataSourceComponent.getFirestations()).thenReturn(firestations);
        Firestation updatedFirestation = firestationRepository.save(newFirestation);
        assertThat(firestations).hasSize(3);
        assertThat(updatedFirestation.getStation()).isSameAs(newFirestation.getStation());
        assertThat(updatedFirestation.getAddress()).isSameAs(newFirestation.getAddress());
    }

    @Test
    public void given_a_firestation_delete_should_remove_the_firestation() throws Exception{
        when(dataSourceComponent.getFirestations()).thenReturn(firestations);
        assertThat(firestations).hasSize(3);
        firestationRepository.delete(firestation);
        assertThat(firestations).hasSize(2);
    }

    @Test
    public void given_an_id_findbyid_should_return_a_firestation() throws Exception{
        when(dataSourceComponent.getFirestations()).thenReturn(firestations);
        Firestation foundFirestation = firestationRepository.findById(2);
        assertThat(foundFirestation.getStation()).isSameAs(firestation1.getStation());
        assertThat(foundFirestation.getAddress()).isSameAs(firestation1.getAddress());
    }

    @Test
    public void given_an_address_findbyaddress_should_return_a_firestation() throws Exception{
        when(dataSourceComponent.getFirestations()).thenReturn(firestations);
        Firestation foundFirestation = firestationRepository.findByAddress("53 rue soglo");
        assertThat(foundFirestation.getStation()).isSameAs(firestation2.getStation());
        assertThat(foundFirestation.getAddress()).isSameAs(firestation2.getAddress());
    }

}
