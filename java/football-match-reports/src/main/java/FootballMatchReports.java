public class FootballMatchReports {    
    public static String onField(int shirtNum) {
        String report;
        switch (shirtNum) {
            case 1 : {
                report = "goalie";
                break;
            }
            case 2 : {
                report = "left back";
                break;
            }
            case 3 :
            case 4 : {
                report = "center back";
                break;
            }
            case 5 : {
                report = "right back";
                break;
            }
            case 6 :
            case 7 :
            case 8 : {
                report = "midfielder";
                break;
            }
            case 9 : {
                report = "left wing";
                break;
            }
            case 10 : {
                report = "striker";
                break;
            }
            case 11 : {
                report = "right wing";
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        return report;
    }
}
