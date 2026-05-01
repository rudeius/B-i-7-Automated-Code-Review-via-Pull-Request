package com.example.bank_system;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Đại diện cho một giao dịch ngân hàng.
 */
public class Transaction {

    private static final Logger LOG = LoggerFactory.getLogger(Transaction.class);

    /** Nạp tiền vãng lai. */
    public static final int TYPE_DEPOSIT_CHECKING = 1;
    /** Rút tiền vãng lai. */
    public static final int TYPE_WITHDRAW_CHECKING = 2;
    /** Nạp tiền tiết kiệm. */
    public static final int TYPE_DEPOSIT_SAVINGS = 3;
    /** Rút tiền tiết kiệm. */
    public static final int TYPE_WITHDRAW_SAVINGS = 4;

    private int type;
    private double amount;
    private double initialBalance;
    private double finalBalance;

    /**
     * Khởi tạo giao dịch.
     *
     * @param type loại giao dịch
     * @param amount số tiền
     * @param initialBalance số dư trước giao dịch
     * @param finalBalance số dư sau giao dịch
     */
    public Transaction(int type, double amount,
                       double initialBalance, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    /**
     * Trả về loại giao dịch.
     *
     * @return loại giao dịch
     */
    public int getType() {
        return type;
    }

    /**
     * Cập nhật loại giao dịch.
     *
     * @param type loại giao dịch mới
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Trả về số tiền giao dịch.
     *
     * @return số tiền
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Cập nhật số tiền giao dịch.
     *
     * @param amount số tiền mới
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Trả về số dư trước giao dịch.
     *
     * @return số dư ban đầu
     */
    public double getInitialBalance() {
        return initialBalance;
    }

    /**
     * Cập nhật số dư trước giao dịch.
     *
     * @param initialBalance số dư ban đầu mới
     */
    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    /**
     * Trả về số dư sau giao dịch.
     *
     * @return số dư cuối
     */
    public double getFinalBalance() {
        return finalBalance;
    }

    /**
     * Cập nhật số dư sau giao dịch.
     *
     * @param finalBalance số dư cuối mới
     */
    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    /**
     * Trả về tên loại giao dịch dạng chuỗi.
     *
     * @param transactionType mã loại giao dịch
     * @return tên loại giao dịch
     */
    public static String getTypeString(int transactionType) {
        switch (transactionType) {
            case TYPE_DEPOSIT_CHECKING:
                return "Nạp tiền vãng lai";
            case TYPE_WITHDRAW_CHECKING:
                return "Rút tiền vãng lai";
            case TYPE_DEPOSIT_SAVINGS:
                return "Nạp tiền tiết kiệm";
            case TYPE_WITHDRAW_SAVINGS:
                return "Rút tiền tiết kiệm";
            default:
                return "Không rõ";
        }
    }

    /**
     * Trả về tóm tắt giao dịch dạng chuỗi.
     *
     * @return chuỗi tóm tắt giao dịch
     */
    public String getTransactionSummary() {
        LOG.debug("Tạo summary cho giao dịch loại: {}", this.type);

        String typeStr = getTypeString(type);
        String initBal = String.format(Locale.US, "%.2f", initialBalance);
        String amtStr = String.format(Locale.US, "%.2f", amount);
        String finalBal = String.format(Locale.US, "%.2f", finalBalance);

        return "- Kiểu giao dịch: " + typeStr
                + ". Số dư ban đầu: $" + initBal
                + ". Số tiền: $" + amtStr
                + ". Số dư cuối: $" + finalBal + ".";
    }
}
