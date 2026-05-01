package com.example.bank_system;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Cung cấp các chức năng cơ bản: nạp tiền, rút tiền, lưu lịch sử giao dịch.
 */
public abstract class Account {

    private static final Logger LOG = LoggerFactory.getLogger(Account.class);

    /** Loại tài khoản vãng lai. */
    public static final String CHECKING_TYPE = "CHECKING";

    /** Loại tài khoản tiết kiệm. */
    public static final String SAVINGS_TYPE = "SAVINGS";

    private long accountNumber;
    private double balance;
    protected List<Transaction> transactionList;

    /**
     * Khởi tạo tài khoản với số tài khoản và số dư ban đầu.
     *
     * @param accountNumber số tài khoản
     * @param balance       số dư ban đầu
     */
    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionList = new ArrayList<>();
    }

    /**
     * Trả về số tài khoản.
     *
     * @return số tài khoản
     */
    public long getAccountNumber() {
        return accountNumber;
    }

    /**
     * Cập nhật số tài khoản.
     *
     * @param accountNumber số tài khoản mới
     */
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Trả về số dư hiện tại.
     *
     * @return số dư
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Cập nhật số dư tài khoản.
     *
     * @param balance số dư mới
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Trả về danh sách giao dịch.
     *
     * @return danh sách giao dịch
     */
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    /**
     * Cập nhật danh sách giao dịch.
     *
     * @param transactionList danh sách giao dịch mới
     */
    public void setTransactionList(List<Transaction> transactionList) {
        if (transactionList == null) {
            this.transactionList = new ArrayList<>();
        } else {
            this.transactionList = transactionList;
        }
    }

    /**
     * Nạp tiền vào tài khoản.
     *
     * @param amount số tiền cần nạp
     */
    public abstract void deposit(double amount);

    /**
     * Rút tiền từ tài khoản.
     *
     * @param amount số tiền cần rút
     */
    public abstract void withdraw(double amount);

    /**
     * Thực hiện logic nạp tiền cơ bản.
     *
     * @param amount số tiền cần nạp
     * @throws InvalidFundingAmountException nếu số tiền không hợp lệ
     */
    protected void doDepositing(double amount) throws InvalidFundingAmountException {
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        balance += amount;
    }

    /**
     * Thực hiện logic rút tiền cơ bản.
     *
     * @param amount số tiền cần rút
     * @throws InvalidFundingAmountException nếu số tiền không hợp lệ
     * @throws InsufficientFundsException    nếu số dư không đủ
     */
    protected void doWithdrawing(double amount)
            throws InvalidFundingAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(amount);
        }
        balance -= amount;
    }

    /**
     * Thêm một giao dịch vào danh sách.
     *
     * @param transaction giao dịch cần thêm
     */
    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionList.add(transaction);
        }
    }

    /**
     * Lấy lịch sử giao dịch dạng chuỗi.
     *
     * @return chuỗi lịch sử giao dịch
     */
    public String getTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");

        for (int i = 0; i < transactionList.size(); i++) {
            sb.append(transactionList.get(i).getTransactionSummary());
            if (i < transactionList.size() - 1) {
                sb.append("\n");
            }
        }

        LOG.debug("Đã lấy lịch sử cho tài khoản: {}", accountNumber);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account other = (Account) obj;
        return this.accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return (int) (accountNumber ^ (accountNumber >>> 32));
    }
}