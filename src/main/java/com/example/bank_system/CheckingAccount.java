package com.example.bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản vãng lai (Checking Account).
 * Cho phép nạp tiền và rút tiền không giới hạn.
 */
public class CheckingAccount extends Account {

    private static final Logger LOG = LoggerFactory.getLogger(CheckingAccount.class);

    /**
     * Khởi tạo tài khoản vãng lai.
     *
     * @param accountNumber số tài khoản
     * @param balance       số dư ban đầu
     */
    public CheckingAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_CHECKING,
                    amount,
                    initialBalance,
                    finalBalance);
            addTransaction(transaction);
            LOG.info("Nạp tiền vãng lai thành công: +{} vào tài khoản {}",
                    amount, getAccountNumber());
        } catch (InvalidFundingAmountException e) {
            LOG.error("Lỗi nạp tiền vãng lai: {}", e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            doWithdrawing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_WITHDRAW_CHECKING,
                    amount,
                    initialBalance,
                    finalBalance);
            addTransaction(transaction);
            LOG.info("Rút tiền vãng lai thành công: -{} từ tài khoản {}",
                    amount, getAccountNumber());
        } catch (InvalidFundingAmountException | InsufficientFundsException e) {
            LOG.error("Lỗi rút tiền vãng lai: {}", e.getMessage());
        }
    }
}
