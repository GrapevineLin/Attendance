package edu.ui.cli;



import edu.bean.Employee;
import edu.bean.Leave;
import edu.service.impl.EmployeeService;
import edu.service.impl.LeaveService;
import edu.service.impl.impl.EmployeeServiceImpl;
import edu.service.impl.impl.LeaveServiceImpl;


import java.nio.file.WatchKey;
import java.util.Date;
import java.util.List;

public class test {
    private static  EmployeeService employeeService = new EmployeeServiceImpl();

    public static void main(String[] args) {
        //list();
        //delete();
       // insert();
       // load();
        //update();
        L();
    }


    private static void L(){
        LeaveService leaveService=new LeaveServiceImpl();
        List<Leave> list = leaveService.pagerByName("Z",1L,10L);
        for (Leave item : list) {
            System.out.println(item.getEmpName() + "\t");
            System.out.println(item.getEmpCode() + "\n");
        }
    }
    private static void time(){


    }




    private static void list() {
        List<Employee> list = employeeService.list();
        for (Employee item : list) {
            System.out.println(item.getEmpId() + "\t");
            System.out.println(item.getEmpName() + "\t");
            System.out.println(item.getEmpCode() + "\n");
        }


    }

    private static void delete() {
        LeaveService leaveService = new LeaveServiceImpl();
        System.out.println(leaveService.delete(3L));
    }
    private static void load(){
        Employee item = employeeService.load(3L);
        System.out.println(item.getEmpId() + "\t");
        System.out.println(item.getEmpName() + "\t");
        System.out.println(item.getEmpCode() + "\n");

    }
    private static void update(){
        Employee employee = new Employee();
         employee.setEmpId(5L);
        employee.setEmpCode("K");
        employee.setEmpName("WK");
        employee.setSex("男");
        employee.setAge(20L);
        employee.setJobId(1L);
        employeeService.update(employee);
    }

    private static void insert() {
        Employee employee = new Employee();
        //employee.setEmpId(4L);
        employee.setEmpCode("WK");
        employee.setEmpName("WA");
        employee.setSex("男");
        employee.setAge(20L);


        employeeService.insert(employee);
    }

}