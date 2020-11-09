package com.ihi.pregnancytracker.serviceimpl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import com.ihi.pregnancytracker.beans.User;
import com.ihi.pregnancytracker.service.FhirService;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FhirServiceImpl implements FhirService {

  Logger logger = LoggerFactory.getLogger(FhirServiceImpl.class);

  private IGenericClient client = null;

  public FhirServiceImpl() {
    FhirContext ctx = FhirContext.forDstu3();
    client = ctx.newRestfulGenericClient("https://r3.smarthealthit.org");
  }

  @Override
  public User getIDByPatientName(String name) {
    User user = null;



    List<String> idList = new ArrayList<String>();
    List<Patient> patientList = new ArrayList<Patient>();

    Bundle bundle = (Bundle) client.search().forResource(Patient.class).where(Patient.NAME.matches().value(name)).returnBundle(Bundle.class).execute();
    patientList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(), bundle, Patient.class));

    while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
      bundle = client.loadPage().next(bundle).execute();
      patientList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(), bundle, Patient.class));
    }

    System.out.println("patientList:" + patientList.size());

    for (Patient patient : patientList) {
      if (patient.getBirthDate() != null ) {
        user = new User();
        user.setFirstName(patient.getName().get(0).getGiven().toString());
        user.setLastName(patient.getName().get(0).getFamilyElement().toString());
        user.setGender(patient.getGender().toString());
        user.setBirthdate(patient.getBirthDate());
        user.setHeight("185 cm");
        user.setWeight("80 kg");
        idList.add(patient.getIdElement().getIdPart());
      }
    }
    return user;
    
  }

}
