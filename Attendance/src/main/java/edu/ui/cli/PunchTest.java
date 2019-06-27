package edu.ui.cli;

import edu.bean.Employee;
import edu.bean.PunchCard;
import edu.service.impl.EmployeeService;
import edu.service.impl.PunchCardService;
import edu.service.impl.impl.EmployeeServiceImpl;
import edu.service.impl.impl.PunchCardServiceImpl;

import java.util.List;

public class PunchTest {
    private static PunchCardService punchCardService = new PunchCardServiceImpl();
    private static EmployeeService employeeService = new EmployeeServiceImpl();

    public static void main(String[] args) {
        List<PunchCard> punchlist = punchCardService.list();
        for (PunchCard i : punchlist) {
            System.out.println(i.getPunchId());
            System.out.println(i.getEmpName());
            System.out.println(i.getEmpCode());
            System.out.println(i.getDate());
//        Employee employee = employeeService.loadByName("admin");
////        PunchCard punchCard = punchCardService.loadByName("admin");
//        System.out.println(employee.getEmpName());
        }
    }
}

