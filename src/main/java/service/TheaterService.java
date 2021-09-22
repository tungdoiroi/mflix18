package service;

import DAO.MongoDB.AbsDAO;
import DAO.MongoDB.TheaterDAO;
import com.mongodb.client.FindIterable;
import lombok.Data;
import model.Geo;
import model.Theater;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/theater")
public class TheaterService extends AbsDAO {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/geojson")
    public GeoJSON getGeoJSON() {
        GeoJSON geoJson = new GeoJSON();
        geoJson.setType("FeatureCollection");
        geoJson.setFeatures(new ArrayList<>());
        FindIterable<Theater> list = new TheaterDAO().getListTheater();
        list.forEach(theater -> {
            Feature feature = new Feature();
            feature.setType(theater.getLocation().getGeo().getType());
            feature.setGeometry(theater.getLocation().getGeo());
            geoJson.getFeatures().add(feature);

            feature.setProperties(new Properties());
            feature.getProperties().setAddress(theater.getLocation().getAddress().getStreet1() );
            feature.getProperties().setState(theater.getLocation().getAddress().getState() );
            feature.getProperties().setAddress(theater.getLocation().getAddress().getCity() );

        });
        return geoJson;
    }
}

@Data
class GeoJSON {
    private String type;
    private ArrayList<Feature> features;
}

@Data
class Feature {
    private String type;
    private Geo geometry;
    private Properties properties;
}

@Data
class Properties {
    private String address;
    private String state;
    private String city;
}

