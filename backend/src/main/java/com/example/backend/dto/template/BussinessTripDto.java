package com.example.backend.dto.template;

import com.example.backend.entity.mongo.BussinessTrip;
import com.example.backend.entity.mongo.Template;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class BussinessTripDto extends TemplateDto{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String location;
    private String reason;


    @Override
    public Template<BussinessTrip> toTemplateEntity(List<Long> approvers, List<Long> refs) {

        BussinessTrip bussinessTrip = BussinessTrip.builder()
                .title(this.getTitle())
                .startDate(this.startDate)
                .endDate(this.endDate)
                .location(this.location)
                .reason(this.reason)
                .build();

        return Template.<BussinessTrip>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .refList(refs)
                .approverList(approvers)
                .typeData(bussinessTrip)
                .build();
    }


}
