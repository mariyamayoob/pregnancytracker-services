package com.ihi.pregnancytracker.serviceimpl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import com.ihi.pregnancytracker.beans.FhirEntity;
import com.ihi.pregnancytracker.beans.User;
import com.ihi.pregnancytracker.service.FhirService;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    public User getPatientDetailsById(String id) {
        User user = new User();
        Patient patient = client.read().resource(Patient.class).withId(id).execute();
        user.setFirstName(patient.getName().get(0).getGiven().get(0).toString());
        user.setLastName(patient.getName().get(0).getFamilyElement().toString());
        user.setGender(patient.getGender().toString());
        user.setDateOfBirth(patient.getBirthDate());
        user.setAge(getAge(user.getDateOfBirth()));
        user.setUserConditionList(getConditions(id));
        user.setUserMedicationList(getMedications(id));
        setObservationValues(user, id);
        return user;

    }

    private double getAge(Date dateOfBirth) {
        LocalDate dob = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(dob, LocalDate.now());
        return p.getYears();
    }

    private void setObservationValues(User user, String id) {
        Bundle bundle = client.search().forResource(Observation.class).where(Observation.PATIENT.hasId(id)).returnBundle(Bundle.class).execute();

        user.setHeight(getObservation(bundle, "8302-2", null));
        user.setWeight(getObservation(bundle, "29463-7", null));
        user.setGlucose(getObservation(bundle, "2339-0", null));
        user.setLdl(getObservation(bundle, "2089-1", null));
        user.setHdl(getObservation(bundle, "2085-9", null));
        user.setTotalCholesterol(getObservation(bundle, "2093-3", null));
        user.setBmi(getObservation(bundle, "39156-5", null));
        user.setSys(getObservation(bundle, "55284-4", "8480-6"));
        user.setDia(getObservation(bundle, "55284-4", "8462-4"));
        user.setCalcium(getObservation(bundle, "49765-1", null));
        user.setHemoglobin(getObservation(bundle, "4548-4", null));



    }

    public List<FhirEntity> getMedications(String id) {
        List<FhirEntity> userMedications = new ArrayList<>();

        try {
            Bundle bundle = (Bundle) client.search().forResource(MedicationRequest.class).where(new ReferenceClientParam("patient").hasId(id)).execute();
            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                FhirEntity userMedication = new FhirEntity();
                MedicationRequest medicationRequest = (MedicationRequest) entry.getResource();
                userMedication.setStatus(medicationRequest.getStatus().getDisplay());

                /*CodeableConcept med = medicationRequest.getMedicationCodeableConcept();
                System.out.println(med.getCoding());
                userMedication.setName(medicationRequest.getMedicationCodeableConcept().getCoding().get(0).getDisplay());*/

                String reference = medicationRequest.getMedicationReference().getReference().substring(11);
                Bundle bundle1 = (Bundle) client.search().forResource(Medication.class).where(new ReferenceClientParam("_id").hasId(reference)).execute();
                if (!bundle1.getEntry().isEmpty())
                    userMedication.setName(((Medication) bundle1.getEntry().get(0).getResource()).getCode().getCoding().get(0).getDisplay());
                userMedications.add(userMedication);
            }
        } catch (FHIRException e) {
            //e.printStackTrace();
            logger.error("Exception in FhirServiceImpl: getMedications ::" + e.getMessage());
        }

        return userMedications;
    }


    public static void main(String[] args) {
        FhirServiceImpl fhirService = new FhirServiceImpl();
        System.out.println(fhirService.getMedications("f7efc381-b774-4548-beb8-f4e030313860"));

        //System.err.println(fhirService.getObservation("f7efc381-b774-4548-beb8-f4e030313860","55284-4" ,"8480-6" ));
    }

    public List<FhirEntity> getConditions(String id) {
        List<FhirEntity> userConditions = new ArrayList<>();
        Bundle bundle = (Bundle) client.search().forResource(Condition.class).where(new ReferenceClientParam("patient").hasId(id)).execute();
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            Condition entryResource = (Condition) entry.getResource();
            FhirEntity userCondition = new FhirEntity();
            userCondition.setName(entryResource.getCode().getCoding().get(0).getDisplay());
            userCondition.setStatus(entryResource.getClinicalStatus().getDisplay());
            userConditions.add(userCondition);
        }
        return userConditions;
    }

    public String getObservation(Bundle bundle, String loincCode, String componentCode) {
        String ret_val = "n/a";
        if (!bundle.getEntry().isEmpty()) {
            for (int i = 0; i < bundle.getEntry().size(); i++) {
                Observation observation = (Observation) bundle.getEntry().get(i).getResource();
                //System.out.println(observation.getCode().getCoding().get(0).getCode()+ " " + loincCode);
                if ((observation.getCode().getCoding().get(0).getCode()).equalsIgnoreCase(loincCode)) {
                    if (componentCode == null) {
                        if (observation.getValue() != null) {
                            try {
                                double value = round(((Quantity) observation.getValue()).getValue().doubleValue());
                                String unit = (observation.getValueQuantity().getUnit());
                                ret_val = value + " " + unit;
                                return ret_val;
                            } catch (FHIRException e) {
                                logger.error("Exception in FhirServiceImpl: getObservation ::" + e.getMessage());
                            }
                        }
                    } else {
                        List<Observation.ObservationComponentComponent> components = observation.getComponent();
                        for (Observation.ObservationComponentComponent component : components) {
                            if ((component.getCode().getCoding().get(0).getCode()).equalsIgnoreCase(componentCode)) {
                                try {
                                    double value = round(component.getValueQuantity().getValue().doubleValue());
                                    String unit = (component.getValueQuantity().getUnit());
                                    ret_val = value + " " + unit;
                                    return ret_val;
                                } catch (Exception e) {
                                    logger.error("Exception in FhirServiceImpl: getObservation ::" + e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
        return ret_val;
    }

    private static double round(double value) {
        //REFERENCE: https://www.baeldung.com/java-round-decimal-number
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}