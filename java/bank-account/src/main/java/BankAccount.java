public class BankAccount {

    private static final String CANNOT_WITHDRAW_MONEY_FROM_AN_EMPTY_ACCOUNT =
            "Cannot withdraw money from an empty account";
    private static final String ACCOUNT_CLOSED = "Account closed";
    private static final String CANNOT_DEPOSIT_OR_WITHDRAW_NEGATIVE_AMOUNT =
            "Cannot deposit or withdraw negative amount";
    private static final String CANNOT_WITHDRAW_MORE_MONEY_THAN_IS_CURRENTLY_IN_THE_ACCOUNT =
            "Cannot withdraw more money than is currently in the account";
    private int balance;
    private boolean active = false;

    public BankAccount() {
        this.balance = 0;
    }

    public void open() {
        active = true;
    }

    public void close() {
        active = false;
    }

    public int getBalance() throws BankAccountActionInvalidException {
        checkAccountOpen();
        return balance;
    }

    public synchronized void deposit(int i) throws BankAccountActionInvalidException {
        checkAccountOpen();
        checkAmountPositive(i);
        balance += i;
    }

    public synchronized void withdraw(int i) throws BankAccountActionInvalidException {
        checkAccountOpen();
        checkAccountNotEmpty();
        checkAmountPositive(i);
        checkBalanceMoreThanAmount(i);
        balance -= i;
    }

    private void checkBalanceMoreThanAmount(int i) throws BankAccountActionInvalidException {
        if (i > balance) {
            throw new BankAccountActionInvalidException(
                    CANNOT_WITHDRAW_MORE_MONEY_THAN_IS_CURRENTLY_IN_THE_ACCOUNT);
        }
    }

    private void checkAmountPositive(int i) throws BankAccountActionInvalidException {
        if (i <= 0) {
            throw new BankAccountActionInvalidException(CANNOT_DEPOSIT_OR_WITHDRAW_NEGATIVE_AMOUNT);
        }
    }

    private void checkAccountNotEmpty() throws BankAccountActionInvalidException {
        if (balance <= 0) {
            throw new BankAccountActionInvalidException(CANNOT_WITHDRAW_MONEY_FROM_AN_EMPTY_ACCOUNT);
        }
    }

    private void checkAccountOpen() throws BankAccountActionInvalidException {
        if (!active) {
            throw new BankAccountActionInvalidException(ACCOUNT_CLOSED);
        }
    }

}