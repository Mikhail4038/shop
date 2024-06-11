package com.keiko.addressservice.request;

import com.keiko.commonservice.entity.resource.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GeocodeRequest {
    private Address address;
}
