package com.microservicesteam.nutaxi.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("PMD")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RouteRequest {

    @Getter
    private String origin;

    @Getter
    private String destination;

    @Getter
    private String language;

}
