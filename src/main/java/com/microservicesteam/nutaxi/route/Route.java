package com.microservicesteam.nutaxi.route;

import java.io.Serializable;
import java.util.List;

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
public class Route implements Serializable {

    private static final long serialVersionUID = -962505687214057695L;

    @Getter
    private List<String> overviewPolylines;

}
