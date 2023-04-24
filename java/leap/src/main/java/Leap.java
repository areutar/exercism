class Leap {

    boolean isLeapYear(int year) {
        boolean fourDivided = (year%4 == 0);
		boolean hundridDivided = (year%100 == 0);
		boolean fourHundridDivided = (year%400 == 0);		 
		return !(!fourDivided | (hundridDivided & !fourHundridDivided));
    }

}
