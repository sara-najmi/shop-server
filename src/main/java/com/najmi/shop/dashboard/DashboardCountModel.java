package com.najmi.shop.dashboard;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCountModel {

    private Integer userCount;

    private Integer orderCount;

    private Integer productCount;

    private Integer orderCountDelivered;

    private Integer orderCountNotDelivered;

    private List<Object[]> chart;
}
