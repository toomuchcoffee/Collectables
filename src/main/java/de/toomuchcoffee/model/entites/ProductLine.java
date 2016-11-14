package de.toomuchcoffee.model.entites;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public enum ProductLine {
    // inspired by JediBusiness.com
    KENNER("Kenner", 1978, 1985, newHashSet("vintage")),
    POTF2("Power of the Force", 1995, 2000, newHashSet()),
    SOTE("Shadows of the Empire", 1996, 1996, newHashSet()),
    EPISODE1("Episode 1", 1999, 2000, newHashSet()),
    POTJ("Power of the Jedi", 2000, 2002, newHashSet()),
    SAGA("Saga", 2002, 2004, newHashSet()),
    DC("Star Tours", 2002, null, newHashSet()),
    OCW("Clone Wars", 2003, 2005, newHashSet()),
    CWANIMATED("Clone Wars Animated Style", 2003, 2005, newHashSet()),
    OTC("Original Trilogy Collection", 2004, 2005, newHashSet()),
    ROTS("Revenge of the Sith", 2005, 2006, newHashSet()),
    TSC("The Saga Collection", 2006, 2007, newHashSet()),
    TAC("The Anniversary Collection", 2007, 2008, newHashSet()),
    TLC("The Legacy Collection", 2008, 2010, newHashSet()),
    TCW("The Clone Wars", 2008, 2013, newHashSet()),
    SOTDS("Shadow of the Dark Side", 2010, 2012, newHashSet()),
    TVC("The Vintage Collection", 2010, 2013, newHashSet()),
    DTF("Discover the Force", 2012, 2013, newHashSet()),
    MH("Movie Heroes", 2012, 2013, newHashSet()),
    TLC13("The Legacy Collection 2013", 2013, 2013, newHashSet("tlc")),
    SL("Saga Legends", 2013, 2014, newHashSet()),
    TBS("The Black Series", 2013, null, newHashSet()),
    TBS6("The Black Series 6 inch", 2013, null, newHashSet("6 scale", "6 inch")),
    SWR("Star Wars Rebels Saga Legends", 2014, 2015, newHashSet()),
    TFA("The Force Awakens", 2015, 2016, newHashSet()),
    RO("Rogue One", 2016, 2017, newHashSet());

    private String verbatim;

    private Integer startYear;

    private Integer endYear;

    private Set<String> tags;

    ProductLine(String verbatim, Integer startYear, Integer endYear, Set<String> tags) {
        this.verbatim = verbatim;
        this.startYear = startYear;
        this.endYear = endYear;
        this.tags = tags;
    }

    public String getVerbatim() {
        return verbatim;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public Set<String> getTags() {
        return tags;
    }
}
