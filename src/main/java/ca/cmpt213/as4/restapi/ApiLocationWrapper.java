package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.Location;

import java.util.ArrayList;
import java.util.List;

public class ApiLocationWrapper {
    public int x;
    public int y;

    public static ApiLocationWrapper makeFromCellLocation(Location cell) {
        ApiLocationWrapper location = new ApiLocationWrapper();
        location.x = cell.getCol();
        location.y = cell.getRow();

        return location;
    }

    public static List<ApiLocationWrapper> makeFromCellLocations(Iterable<Location> cells) {
        List<ApiLocationWrapper> locations = new ArrayList<>();

        for (Location cell: cells) {
            ApiLocationWrapper location = new ApiLocationWrapper().makeFromCellLocation(cell);
            locations.add(location);
        }

        return locations;
    }
}