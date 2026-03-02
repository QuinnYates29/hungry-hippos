package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class BasketFileDAO extends CupboardFileDAO implements CupboardDAO {

    public BasketFileDAO(
        @Value("${basket.file}") String filename,
        ObjectMapper objectMapper
    ) throws IOException {
        super(filename, objectMapper);
    }
}