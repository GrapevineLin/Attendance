package edu.ui.cli;

import edu.bean.Station;
import edu.service.impl.StationService;
import edu.service.impl.StationService;
import edu.service.impl.impl.StationServiceImpl;
import edu.service.impl.impl.StationServiceImpl;

import java.util.List;

public class StartStation {
    public static void main(String[] args) {
        list();
    }

    private static StationService stationService = new StationServiceImpl();

    private static void update() {
        Station bean = new Station();
        /*bean.setDepCode("sss");
        bean.setDepName("ssssd");*/
        stationService.update(bean);

    }

    private static void list() {
        List<Station> list = stationService.pagerByName( "q",1L, 2L);
        System.out.println("userId\tuserName\tnickName");
        for (Station item : list) {
            System.out.print(item.getJobId() + "\t");
            System.out.print(item.getJobCode() + "\t");
            System.out.print(item.getJobName() + "\n");
        }
    }

    private static void load() {
        Station item = stationService.loadByName("q");
        System.out.println("userId\tuserName\tnickName");
        System.out.print(item.getJobId() + "\t");
        System.out.print(item.getJobCode() + "\t");
        System.out.print(item.getJobName() + "\n");

    }
}
