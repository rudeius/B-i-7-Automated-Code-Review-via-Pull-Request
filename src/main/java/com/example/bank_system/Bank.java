package com.example.bank_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho ngân hàng, quản lý danh sách khách hàng.
 * Cung cấp chức năng đọc, sắp xếp và hiển thị thông tin khách hàng.
 */
public class Bank {

    private static final Logger LOG = LoggerFactory.getLogger(Bank.class);

    /** Regex pattern để nhận diện số CMND (9 chữ số). */
    private static final String ID_NUMBER_PATTERN = "\\d{9}";

    private List<Customer> customerList;

    /**
     * Khởi tạo ngân hàng với danh sách khách hàng rỗng.
     */
    public Bank() {
        this.customerList = new ArrayList<>();
    }

    /**
     * Trả về danh sách khách hàng.
     *
     * @return danh sách khách hàng
     */
    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Cập nhật danh sách khách hàng.
     *
     * @param customerList danh sách mới
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc danh sách khách hàng từ InputStream.
     *
     * @param inputStream luồng dữ liệu đầu vào
     */
    public void readCustomerList(InputStream inputStream) {
        LOG.info("Bắt đầu đọc dữ liệu khách hàng...");

        if (inputStream == null) {
            LOG.warn("InputStream is null, bỏ qua đọc dữ liệu.");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Customer current = null;

        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int lastSpace = line.lastIndexOf(' ');
                if (lastSpace <= 0) {
                    continue;
                }

                String token = line.substring(lastSpace + 1).trim();

                if (token.matches(ID_NUMBER_PATTERN)) {
                    current = parseCustomerLine(line, lastSpace, token);
                } else if (current != null) {
                    parseAccountLine(line, current);
                }
            }
        } catch (IOException e) {
            LOG.error("Lỗi khi đọc dữ liệu khách hàng: {}", e.getMessage(), e);
        }

        LOG.info("Hoàn tất đọc dữ liệu. Tổng khách hàng: {}", customerList.size());
    }

    /**
     * Phân tích dòng chứa thông tin khách hàng.
     *
     * @param line      dòng dữ liệu
     * @param lastSpace vị trí khoảng trắng cuối cùng
     * @param token     chuỗi số CMND
     * @return đối tượng Customer mới
     */
    private Customer parseCustomerLine(String line, int lastSpace, String token) {
        String name = line.substring(0, lastSpace).trim();
        Customer customer = new Customer(Long.parseLong(token), name);
        customerList.add(customer);
        LOG.info("Thêm khách hàng: {}", name);
        return customer;
    }

    /**
     * Phân tích dòng chứa thông tin tài khoản và thêm vào khách hàng.
     *
     * @param line    dòng dữ liệu
     * @param current khách hàng hiện tại
     */
    private void parseAccountLine(String line, Customer current) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
            LOG.warn("Dòng tài khoản không hợp lệ: {}", line);
            return;
        }

        long accountNum = Long.parseLong(parts[0]);
        double accountBalance = Double.parseDouble(parts[2]);

        if (Account.CHECKING_TYPE.equals(parts[1])) {
            current.addAccount(new CheckingAccount(accountNum, accountBalance));
        } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
            current.addAccount(new SavingsAccount(accountNum, accountBalance));
        } else {
            LOG.warn("Loại tài khoản không xác định: {}", parts[1]);
        }
    }

    /**
     * Trả về thông tin khách hàng sắp xếp theo số CMND.
     *
     * @return chuỗi thông tin khách hàng
     */
    public String getCustomersInfoByIdOrder() {
        Collections.sort(customerList,
                Comparator.comparingLong(Customer::getIdNumber));
        return buildCustomerInfo(customerList);
    }

    /**
     * Trả về thông tin khách hàng sắp xếp theo tên, rồi theo số CMND.
     *
     * @return chuỗi thông tin khách hàng
     */
    public String getCustomersInfoByNameOrder() {
        List<Customer> sorted = new ArrayList<>(customerList);
        sorted.sort(Comparator.comparing(Customer::getFullName)
                .thenComparingLong(Customer::getIdNumber));
        return buildCustomerInfo(sorted);
    }

    /**
     * Xây dựng chuỗi thông tin từ danh sách khách hàng.
     *
     * @param customers danh sách khách hàng
     * @return chuỗi thông tin
     */
    private String buildCustomerInfo(List<Customer> customers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.size(); i++) {
            sb.append(customers.get(i).getCustomerInfo());
            if (i < customers.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}