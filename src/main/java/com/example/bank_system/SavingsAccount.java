package com.example.bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm (Savings Account).
 */
public class SavingsAccount extends Account {

    private static final Logger LOG = LoggerFactory.getLogger(SavingsAccount.class);
    private static final double MAX_WITHDRAWAL = 1000.0;
    private static final double MIN_BALANCE = 5000.0;

    /**
     * Khởi tạo tài khoản tiết kiệm.
     *
     * @param accountNumber số tài khoản
     * @param balance số dư ban đầu
     */
    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        LOG.debug("Xử lý nạp tiền tiết kiệm tài khoản {}...", getAccountNumber());
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction t = new Transaction(
                    Transaction.TYPE_DEPOSIT_SAVINGS,
                    amount, initialBalance, finalBalance);
            addTransaction(t);
            LOG.info("Nạp tiền thành công: +{} vào TK {}", amount, getAccountNumber());
        } catch (InvalidFundingAmountException e) {
            LOG.error("Lỗi nạp tiền: {}", e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAWAL) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE) {
                throw new InsufficientFundsException(amount);
            }
            doWithdrawing(amount);
            double finalBalance = getBalance();
            Transaction t = new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS,
                    amount, initialBalance, finalBalance);
            addTransaction(t);
            LOG.info("Rút tiền thành công: -{} từ TK {}. Số dư: {}",
                    amount, getAccountNumber(), finalBalance);
        } catch (InvalidFundingAmountException | InsufficientFundsException e) {
            LOG.error("Lỗi rút tiền tiết kiệm: {}", e.getMessage());
        }
    }
}