package edu.ui.cli;

import edu.bean.Department;
import edu.service.impl.DepartmentService;
import edu.service.impl.impl.DepartmentServiceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class StartDepartment {
    public static void main(String[] args) {
        departmentService.delete(5L);
    }

    private static DepartmentService departmentService = new DepartmentServiceImpl();

    private static void update() {
        Department bean=new Department();
        bean.setDepId(5L);
        bean.setDepCode("sss");
        bean.setDepName("ssssd");
        departmentService.update(bean);

    }

    private static void list() {
        List<Department> list = departmentService.list();
        System.out.println("userId\tuserName\tnickName");
        for (Department item : list) {
            System.out.print(item.getDepId() + "\t");
            System.out.print(item.getDepCode() + "\t");
            System.out.print(item.getDepName() + "\n");
        }
    }

    private static void load() {
        Department item = departmentService.loadByName("zcs");
        System.out.println("userId\tuserName\tnickName");
        System.out.print(item.getDepId() + "\t");
        System.out.print(item.getDepCode() + "\t");
        System.out.print(item.getDepName() + "\n");
    }

}
