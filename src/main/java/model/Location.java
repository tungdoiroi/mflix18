package model;

import lombok.Data;

@Data
public class Location {
    private Address address;
    private  Geo geo;
}