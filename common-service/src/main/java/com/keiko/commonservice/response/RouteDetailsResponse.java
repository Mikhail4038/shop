package com.keiko.commonservice.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RouteDetailsResponse {
    //The total distance, in kilometers
    private Double distance;
    //The total travel time, in minutes
    private Double time;
}
