package com.microservicesteam.nutaxi.route;

import java.io.Serializable;

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
public class RouteRequest implements Serializable {

    private static final long serialVersionUID = -1116746512744771963L;

    @Getter
    private String origin;

    @Getter
    private String destination;

    @Getter
    private String language;

}
