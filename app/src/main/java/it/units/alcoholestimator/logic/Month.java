package it.units.alcoholestimator.logic;

public enum Month {
    JAN(1, "JAN"),
    FEB(2, "FEB"),
    MAR(3, "MAR"),
    APR(4, "APR"),
    MAY(5, "MAY"),
    JUN(6, "JUN"),
    JUL(7, "JUL"),
    AUG(8, "AUG"),
    SET(9, "SET"),
    OCT(10, "OCT"),
    NOV(11, "NOV"),
    DEC(12, "DEC");


    public int monthNumber;
    public String representation;
    Month(int monthNumber, String representation){
        this.monthNumber = monthNumber;
        this.representation = representation;
    }

    public static Month retrieveByMonthNumber(int monthNumber) {
        switch (monthNumber) {
            case 1:
                return Month.JAN;
            case 2:
                return Month.FEB;
            case 3:
                return Month.MAR;
            case 4:
                return Month.APR;
            case 5:
                return Month.MAY;
            case 6:
                return Month.JUN;
            case 7:
                return Month.JUL;
            case 8:
                return Month.AUG;
            case 9:
                return Month.SET;
            case 10:
                return Month.OCT;
            case 11:
                return Month.NOV;
            case 12:
                return Month.DEC;
            default:
                return null;
        }
    }
}
