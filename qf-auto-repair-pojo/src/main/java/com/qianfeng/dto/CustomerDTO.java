package com.qianfeng.dto;

import lombok.Data;

@Data
public class CustomerDTO {
        private Integer customerId;
        private String name;
        private String phone;
        private String email;
        private String address;
        private String customerType;
}