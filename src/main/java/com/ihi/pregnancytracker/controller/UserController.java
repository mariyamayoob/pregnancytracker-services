package com.ihi.pregnancytracker.controller;

import com.ihi.pregnancytracker.beans.AppUser;
import com.ihi.pregnancytracker.beans.User;
import com.ihi.pregnancytracker.repository.UserRepository;
import com.ihi.pregnancytracker.service.FhirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
  
  Logger logger = LoggerFactory.getLogger(UserController.class);
  
  @Autowired
  private FhirService fhirService;
  
  @GetMapping("/get/patient")
  public User getPatientDetailsById(@RequestParam(value = "id") String id) {
    return fhirService.getPatientDetailsById(id);
  }
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/loginusers")
  public List<AppUser> getUsers(){

    Iterable<AppUser> i=userRepository.findAll();
    List<AppUser> c= new ArrayList<>();
    Iterator<AppUser> itr=i.iterator();
    while(itr.hasNext()){
      AppUser cn=itr.next();
      c.add(cn);
    }
    return c;
  }
  @GetMapping("/loginuser")
  public AppUser getUser(@RequestParam(value = "fhirID") String fhirId){
    AppUser appUser = userRepository.findAppUserByFhirID(fhirId);
    ZoneId easternTime = ZoneId.of("America/Montreal");
    LocalDate newDate = LocalDate.now(easternTime);
    if(appUser!=null && appUser.getLastUpdateDate()!=null) {
      LocalDate dbDate = appUser.getLastUpdateDate();
      appUser.setUpdatedToday(newDate.isEqual(dbDate));
    }
    return appUser;

  }
  @Transactional
  @GetMapping("/updatecalories")
  public int updateCal(@RequestParam(value = "fhirID") String fhirId, @RequestParam(value = "new_calorie") float value){

    int i=userRepository.updateCalorie(fhirId,value);

    return i;
  }

  @Transactional
  @GetMapping("/updateUserVal")
  public int updateCalAndAct(@RequestParam(value = "fhirID") String fhirId, @RequestParam(value = "new_calorie") float cal, @RequestParam(value = "new_activity") float activity){
    LocalDate date = LocalDate.now();
    int i=userRepository.updateUserVal(fhirId,cal, activity, date);
    return i;
  }

  @Transactional
  @GetMapping("/createuser")
  public AppUser createUser(@RequestParam(value = "fhirID") String fhirId, @RequestParam(value = "name") String name, @RequestParam(value = "pregnancy_start_dt")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date, @RequestParam(value = "prepregweight") float prepregweight, @RequestParam(value = "new_calorie") float cal, @RequestParam(value = "new_activity") float activity){
    AppUser appUser = new AppUser();
    appUser.setFhirID(fhirId);
    appUser.setName(name);
    appUser.setC_d1(cal);
    appUser.setA_d1(activity);
    appUser.setPregnancyStartDate(date);
    appUser.setPrepregnacyWeight(prepregweight);

    ZoneId easternTime = ZoneId.of("America/Montreal");

    appUser.setLastUpdateDate(LocalDate.now(easternTime));
    userRepository.save(appUser);

    return getUser(fhirId);

  }
}
