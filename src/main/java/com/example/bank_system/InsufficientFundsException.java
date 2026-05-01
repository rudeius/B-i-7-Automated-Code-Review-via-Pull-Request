package com.example.bank_system;

import java.util.Locale;

/**
 * Ngoại lệ khi số dư tài khoản không đủ để thực hiện giao dịch.
 */
public class InsufficientFundsException extends BankException {

    /**
     * Khởi tạo ngoại lệ với số tiền giao dịch bị từ chối.
     *
     * @param amount số tiền yêu cầu
     */
    public InsufficientFundsException(double amount) {
        super("Số dư tài khoản không đủ $"
                + String.format(Locale.US, "%.2f", amount)
                + " để thực hiện giao dịch");
    }
}
