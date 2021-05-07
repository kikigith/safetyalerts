package com.safetynet.service;

import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.dto.ChildrenCoveredDTO;
import com.safetynet.model.dto.PersonsCoveredByStation;

import java.util.List;

public interface CommonService {
    PersonsCoveredByStation produceStationCoverage(int stationId) throws FirestationNotFoundException;
    ChildrenCoveredDTO produceChildrenAtAddress(String address);
    List<String> getResidentPhoneNumber(int stationId) throws FirestationNotFoundException;
    List<String> getCommunityEmails(String city);
}
