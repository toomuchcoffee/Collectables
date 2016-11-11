package de.toomuchcoffee.model.entites;

public enum ProductLine {
    // inspired by JediBusiness.com
    KENNER("Kenner", 1978, 1985),
    POTF2("Power of the Force", 1995, 2000),
    SOTE("Shadows of the Empire", 1996, 1996),
    EPISODE1("Episode 1", 1999, 2000),
    POTJ("Power of the Jedi", 2000, 2002),
    SAGA("Saga", 2002, 2004),
    DC("Star Tours", 2002, null),
    OCW("Clone Wars", 2003, 2005),
    CWANIMATED("Clone Wars Animated Style", 2003, 2005),
    OTC("Original Trilogy Collection", 2004, 2005),
    ROTS("Revenge of the Sith", 2005, 2006),
    TSC("The Saga Collection", 2006, 2007),
    TAC("The Anniversary Collection", 2007, 2008),
    TLC("The Legacy Collection", 2008, 2010),
    TCW("The Clone Wars", 2008, 2013),
    SOTDS("Shadow of the Dark Side", 2010, 2012),
    TVC("The Vintage Collection", 2010, 2013),
    DTF("Discover the Force", 2012, 2013),
    MH("Movie Heroes", 2012, 2013),
    TLC13("The Legacy Collection 2013", 2013, 2013),
    SL("Saga Legends", 2013, 2014),
    TBS("The Black Series", 2013, null),
    TBS6("The Black Series 6 inch", 2013, null),
    SWR("Star Wars Rebels Saga Legends", 2014, 2015),
    TFA("The Force Awakens", 2015, 2016),
    RO("Rogue One", 2016, 2017);

    public String verbatim;

    public Integer startYear;

    public Integer endYear;

    ProductLine(String verbatim, Integer startYear, Integer endYear) {
        this.verbatim = verbatim;
        this.startYear = startYear;
        this.endYear = endYear;
    }
}
