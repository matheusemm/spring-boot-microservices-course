package com.sivalabs.bookstore.order.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoggedInUserName() {
        return "user";
    }
}
