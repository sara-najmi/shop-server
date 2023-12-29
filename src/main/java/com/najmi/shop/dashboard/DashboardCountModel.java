package com.najmi.shop.dashboard;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCountModel {

    private Integer userCount;

    private Integer orderCount;

    private Integer productCount;

    private Integer orderCountDelivered;

    private Integer orderCountNotDelivered;
}
