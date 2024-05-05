package org.shanthan.customerdataservice.annotation;

import lombok.Getter;

import java.lang.annotation.Annotation;

@Getter
public enum USState {
    ALABAMA("AL", "Alabama"),
    ALASKA("AK", "Alaska"),
    ARIZONA("AZ", "Arizona"),
    ARKANSAS("AR", "Arkansas"),
    CALIFORNIA("CA", "California"),
    COLORADO("CO", "Colorado"),
    CONNECTICUT("CT", "Connecticut"),
    DELAWARE("DE", "Delaware"),
    FLORIDA("FL", "Florida"),
    GEORGIA("GA", "Georgia"),
    HAWAII("HI", "Hawaii"),
    IDAHO("ID", "Idaho"),
    ILLINOIS("IL", "Illinois"),
    INDIANA("IN", "Indiana"),
    IOWA("IA", "Iowa"),
    KANSAS("KS", "Kansas"),
    KENTUCKY("KY", "Kentucky"),
    LOUISIANA("LA", "Louisiana"),
    MAINE("ME", "Maine"),
    MARYLAND("MD", "Maryland"),
    MASSACHUSETTS("MA", "Massachusetts"),
    MICHIGAN("MI", "Michigan"),
    MINNESOTA("MN", "Minnesota"),
    MISSISSIPPI("MS", "Mississippi"),
    MISSOURI("MO", "Missouri"),
    MONTANA("MT", "Montana"),
    NEBRASKA("NE", "Nebraska"),
    NEVADA("NV", "Nevada"),
    NEW_HAMPSHIRE("NH", "New Hampshire"),
    NEW_JERSEY("NJ", "New Jersey"),
    NEW_MEXICO("NM", "New Mexico"),
    NEW_YORK("NY", "New York"),
    NORTH_CAROLINA("NC", "North Carolina"),
    NORTH_DAKOTA("ND", "North Dakota"),
    OHIO("OH", "Ohio"),
    OKLAHOMA("OK", "Oklahoma"),
    OREGON("OR", "Oregon"),
    PENNSYLVANIA("PA", "Pennsylvania"),
    RHODE_ISLAND("RI", "Rhode Island"),
    SOUTH_CAROLINA("SC", "South Carolina"),
    SOUTH_DAKOTA("SD", "South Dakota"),
    TENNESSEE("TN", "Tennessee"),
    TEXAS("TX", "Texas"),
    UTAH("UT", "Utah"),
    VERMONT("VT", "Vermont"),
    VIRGINIA("VA", "Virginia"),
    WASHINGTON("WA", "Washington"),
    WEST_VIRGINIA("WV", "West Virginia"),
    WISCONSIN("WI", "Wisconsin"),
    WYOMING("WY", "Wyoming");

    private final String abbreviation;
    private final String name;

    USState(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public static boolean isValidState(String state) {
        if (state == null || state.isEmpty()) {
            return false;
        }
        for (USState usState : USState.values()) {
            if (usState.abbreviation.equalsIgnoreCase(state) || usState.name.equalsIgnoreCase(state)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (USState state : USState.values()) {
            System.out.printf("%s - %s (%s)%n", state.name(), state.getName(), state.getAbbreviation());
        }
    }

}
