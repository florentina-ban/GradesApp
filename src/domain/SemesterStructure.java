package domain;

import exceptions.DateException;

import java.util.Calendar;

public class SemesterStructure {
    private Calendar beginningDate;
    private Week[] weeks;

    public SemesterStructure(String s) {
        String[] parts = s.split("-");

        this.beginningDate =Calendar.getInstance();
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);
        beginningDate.set(year,month,day);

        Calendar cal=Calendar.getInstance();
        cal.set(year,month,day);
        String aux=Constants.DATE_FORMATTER.format(cal.getTime());
        weeks = new Week[15];
        for (int i=1; i<=14; i++){
            weeks[i]=new Week(aux);
            aux=weeks[i].getEnd();
        }

    }

    public String getBeginningDate() {
        return Constants.DATE_FORMATTER.format(beginningDate.getTime());
    }

    public String getBeginningDateOfWeek(int n){
        if (n<1 || n>14)
            throw new DateException();

        return weeks[n].getBeginning();

    }

    public int getWeek(String s){
        int week=0;
        String[] parts = s.split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,beginningDate.get(Calendar.YEAR));
        cal.set(Calendar.MONTH,beginningDate.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH,beginningDate.get(Calendar.DAY_OF_MONTH));

        for (int i=1; i<14; i++){
            if (cal.compareTo(Week.transformStringToDate(weeks[i].getBeginning()))>=0 && cal.compareTo(Week.transformStringToDate(weeks[i].getEnd()))<0 )
                return i;
            ___________________
        }
        Calendar date =Calendar.getInstance();
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);
        date.set(year,month,day);
        while(date.compareTo(cal)>0){
            week++;
            cal.add(Calendar.DAY_OF_MONTH,7);
        }
        return week;
    }
    public String getSecondSemester(){
        String s = getBeginningDateOfWeek(14);
        String[] parts = s.split("-");

        Calendar aux =Calendar.getInstance();
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);
        aux.set(year,month,day);
        aux.add(Calendar.WEEK_OF_MONTH,6);
        return Constants.DATE_FORMATTER.format(aux.getTime());
    }
}
