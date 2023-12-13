package com.study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    // closed Projections
    // String getUsername();

    // open Projections
    @Value("#{target.username + '' + target.age}")
    String getUsername();
}
