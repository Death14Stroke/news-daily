package com.andruid.magic.newsdaily.eventbus;

public class CountryEvent {
    private final String countryCode;

    public CountryEvent(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}