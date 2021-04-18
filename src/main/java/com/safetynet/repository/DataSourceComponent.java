package com.safetynet.repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.AlertsData;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DataSourceComponent {

    private final Logger logger = LoggerFactory.getLogger(DataSourceComponent.class);

    @Value("${json.file.location}")
    private Resource jsonFile;

    private AlertsData data;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * jsonDataReader - Deserialize json root node from the json data repository
     * into AlertsData class object.
     *
     * @throws JsonMappingException when the json doesn't
     */
    @PostConstruct
    public void loadJsonData() throws JsonMappingException {

        try {
            data = objectMapper.readValue(jsonFile.getFile(), AlertsData.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * serializeDataToFile - Serialize data to json root node in the json data repository
     * @param data
     */
    public void serializeDataToFile(AlertsData data) {
        try (FileOutputStream fos = new FileOutputStream(jsonFile.getFile())) {
            objectMapper.writeValue(fos, data);
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        return data.getPersons();
    }

    public List<Firestation> getFirestations() {
        return data.getFirestations();
    }

    public List<MedicalRecord> getMedicalrecords() {
        return data.getMedicalrecords();
    }

    public AlertsData getData(){
        return  data;
    }
}
