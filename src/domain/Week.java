package domain;

import utils.Constants;

import java.util.Calendar;

public class Week {
    private Calendar beginning;
    private Calendar end;

    public Week(String s) {
        String[] parts = s.split("-");

        this.beginning =transformStringToDate(s);
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);

        beginning.set(year,month,day);
        end = Calendar.getInstance();
        end.set(year,month,day);
        end.add(Calendar.DAY_OF_MONTH,7);

        Calendar christmas = Calendar.getInstance();
        christmas.set(year,11,25);
        Calendar newYear = Calendar.getInstance();
        newYear.set(year+1,0,1);

        beginning.set(year,month,day);


        if (christmas.compareTo(beginning)>0 && christmas.compareTo(end)<0){
                beginning.add(Calendar.DAY_OF_MONTH,7);
                end.add(Calendar.DAY_OF_MONTH,7);
        }
        if (newYear.compareTo(beginning)>0 && newYear.compareTo(end)<0){
            beginning.add(Calendar.DAY_OF_MONTH,7);
            end.add(Calendar.DAY_OF_MONTH,7);
        }
        Calendar easter = getEasterDate(end.get(Calendar.YEAR));
        easter.add(Calendar.DAY_OF_MONTH,1);
        if (easter.compareTo(beginning)>=0 && easter.compareTo(end)<=0){
            beginning.add(Calendar.DAY_OF_MONTH,7);
            end.add(Calendar.DAY_OF_MONTH,7);
            System.out.println(easter.getTime());
        }
    }

    public static Calendar transformStringToDate(String s){
        String[] parts = s.split("-");

        Calendar cal =Calendar.getInstance();
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day= Integer.parseInt(parts[0]);
        cal.set(year,month,day);
        return cal;
    }
    public String getBeginning() {
        return Constants.DATE_FORMATTER.format(beginning.getTime());
    }

    public String getEnd() {
        return Constants.DATE_FORMATTER.format(end.getTime());
    }

    private Calendar getEasterDate(int year){
        int a = year % 4;
        int b = year % 7;
        int c = year % 19;
        int d = (19*c+15) % 30;
        int e = (2*a+4*b-d+34) % 7;
        int month = (d+e+114) / 31;
        int day = ((d+e+114) % 31)+1;
        Calendar easter = Calendar.getInstance();
        easter.set(year,month-1,day);
        easter.add(Calendar.DAY_OF_MONTH,13);
        return easter;
    }
}
