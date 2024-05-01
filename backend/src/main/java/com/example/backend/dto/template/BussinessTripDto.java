package com.example.backend.dto.template;

import com.example.backend.entity.mongo.BussinessTrip;
import com.example.backend.entity.mongo.Template;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class BussinessTripDto extends TemplateDto{

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String reason;


    @Override
    public Template<BussinessTrip> toEntity() {

        BussinessTrip bussinessTrip = BussinessTrip.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .location(this.location)
                .reason(this.reason)
                .build();

        return Template.<BussinessTrip>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .refList(this.getRefList())
                .approverList(this.getApproverList())
                .typeData(bussinessTrip)
                .build();
    }
}
