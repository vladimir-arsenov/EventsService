package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class EventDto {
    private final int id;
    private final String title;
    private final BigDecimal price;

    public EventDto(int id, String title, String price) {
        this.id = id;
        this.title = title;
        Matcher matcher = Pattern.compile("\\d+").matcher(price);
        this.price = new BigDecimal(matcher.find() ? matcher.group() : "0");
    }
}
