package com.example.bank_system;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho một khách hàng ngân hàng.
 * Mỗi khách hàng có số CMND, họ tên và danh sách tài khoản.
 */
public class Customer {

    private long idNumber;
    private String fullName;
    private List<Account> accountList;

    /**
     * Constructor không tham số.
     */
    public Customer() {
        this(0L, "");
    }

    /**
     * Khởi tạo khách hàng với số CMND và họ tên.
     *
     * @param idNumber số CMND
     * @param fullName họ tên đầy đủ
     */
    public Customer(long idNumber, String fullName) {
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.accountList = new ArrayList<>();
    }

    /**
     * Trả về số CMND.
     *
     * @return số CMND
     */
    public long getIdNumber() {
        return idNumber;
    }

    /**
     * Cập nhật số CMND.
     *
     * @param idNumber số CMND mới
     */
    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Trả về họ tên đầy đủ.
     *
     * @return họ tên
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Cập nhật họ tên.
     *
     * @param fullName họ tên mới
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Trả về danh sách tài khoản.
     *
     * @return danh sách tài khoản
     */
    public List<Account> getAccountList() {
        return accountList;
    }

    /**
     * Cập nhật danh sách tài khoản.
     *
     * @param accountList danh sách tài khoản mới
     */
    public void setAccountList(List<Account> accountList) {
        if (accountList == null) {
            this.accountList = new ArrayList<>();
        } else {
            this.accountList = accountList;
        }
    }

    /**
     * Thêm tài khoản cho khách hàng (không trùng lặp).
     *
     * @param account tài khoản cần thêm
     */
    public void addAccount(Account account) {
        if (account == null) {
            return;
        }
        if (!accountList.contains(account)) {
            accountList.add(account);
        }
    }

    /**
     * Xóa tài khoản khỏi khách hàng.
     *
     * @param account tài khoản cần xóa
     */
    public void removeAccount(Account account) {
        if (account == null) {
            return;
        }
        accountList.remove(account);
    }

    /**
     * Trả về thông tin khách hàng dạng chuỗi.
     *
     * @return chuỗi thông tin khách hàng
     */
    public String getCustomerInfo() {
        return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
    }
}
