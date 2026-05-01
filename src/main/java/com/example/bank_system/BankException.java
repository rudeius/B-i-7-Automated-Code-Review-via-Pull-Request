package com.example.bank_system;

/**
 * Ngoại lệ chung trong hệ thống ngân hàng.
 * Là lớp cha cho tất cả các ngoại lệ nghiệp vụ.
 */
public class BankException extends Exception {

    /**
     * Khởi tạo ngoại lệ với thông báo lỗi.
     *
     * @param message thông báo lỗi
     */
    public BankException(String message) {
        super(message);
    }
}
