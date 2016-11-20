package de.toomuchcoffee.view;

public class ProductLineDto {
    private String code;

    public String verbatim;

    public Integer startYear;

    public Integer endYear;

    public ProductLineDto(String code, String verbatim, Integer startYear, Integer endYear) {
        this.code = code;
        this.verbatim = verbatim;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getCode() {
        return code;
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
}
