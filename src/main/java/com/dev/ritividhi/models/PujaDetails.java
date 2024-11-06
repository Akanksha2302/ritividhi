package com.dev.ritividhi.models;

import java.util.List;

import lombok.Data;

@Data
public class PujaDetails {
private String gotra;
private String mode;
private String attendanceType; //inPerson or onBehalf
private List<Temple> templeDetails;

}
//List<PujaDetails>- gotra, mode, inperson/onBehalf, Temple Puja , temple details
