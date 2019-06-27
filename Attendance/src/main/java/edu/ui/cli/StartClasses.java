package edu.ui.cli;

import edu.bean.Classes;
import edu.service.impl.ClassesService;
import edu.service.impl.impl.ClassesServiceImpl;

import java.util.List;

public class StartClasses {
    private static ClassesService classesService = new ClassesServiceImpl();
    public static void main(String[] args) {
        List<Classes> list = classesService.list();
        for (Classes i : list){
            System.out.println(i.getClassId() + " " + i.getAm() +" "+i.getPm());
        }
    }
}
