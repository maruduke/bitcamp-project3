package com.example.backend.entity.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class Report {

    private String title;
    private String detail;
}
