package com.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.AlertsData;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class DataSourceComponent {

    private final Logger logger = LoggerFactory.getLogger(DataSourceComponent.class);

    @Value("${json.file.location}")
    private Resource jsonFile;
    private AlertsData data;

    /**
     * jsonDataReader - Deserialize json root node from the json data repository
     * into AlertsData class object.
     * */
    @PostConstruct
    public void loadJsonData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(jsonFile.getFile(), AlertsData.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /*public void serializeDataToFile(AlertsData data) {
        try (FileOutputStream fos = new FileOutputStream(jsonFile.getFile())) {
            objectMapper.writeValue(fos, data);
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }*/

    public List<Person> getPersons() {
        return data.getPersons();
    }

    public List<Firestation> getFirestations() {
        return data.getFirestations();
    }

    public List<MedicalRecord> getMedicalrecords() {
        return data.getMedicalrecords();
    }

    /*public AlertsData getData(){
        return  data;
    }*/
}
