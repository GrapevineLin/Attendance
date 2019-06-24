package edu.ui.cli;

import edu.bean.User;
import edu.service.impl.UserService;
import edu.service.impl.impl.UserServiceImpl;

import java.util.List;

public class StartUser {
    public static void main(String[] args) {
//        list();
//        load();
        LoadByName();
//        count();
//        page(2L,3L);
//        countByName();
//        pageByName("admin",1L,3L);
    }

    private static UserService memberService = new UserServiceImpl();

//    private static void list() {
//        List<User> list = memberService.list();
//        System.out.println("userId\tuserName\tnickName");
//        for (User item : list) {
//            System.out.println(item.getUserId() + "\t");
//            System.out.println(item.getUserName() + "\t");
//            System.out.println(item.getNickName() + "\n");
//        }
//    }
//
//    private static void load(){
//        User bean = memberService.Load(1L);
//        System.out.println(bean.getUserId() + "\t");
//        System.out.println(bean.getUserName() + "\t");
//        System.out.println(bean.getNickName() + "\n");
//
//    }
    private static void LoadByName(){
        User bean = memberService.LoadByName("admin");
        System.out.println(bean.getId() + "\t");
        System.out.println(bean.getUserName() + "\t");
        System.out.println(bean.getPassWord() + "\n");
    }
//    private static void count(){
//        System.out.println(memberService.count());
//    }
//    private static void page(Long pageNum, Long pageSize){
//        List<User> list = memberService.pager(pageNum,pageSize);
//        for (User item : list) {
//            System.out.println(item.getUserId() + "  ");
//            System.out.println(item.getUserName() + "  ");
//            System.out.println(item.getNickName() + "\n");
//        }
//    }
//    private static void countByName(){
//        System.out.println(memberService.countByName("admin"));
//    }
//    private static void pageByName(String name, Long pageNum, Long pageSize){
//        List<User> list = memberService.pagerByName(name,pageNum,pageSize);
//        for (User item : list) {
//            System.out.println(item.getUserId() + "  ");
//            System.out.println(item.getUserName() + "  ");
//            System.out.println(item.getNickName() + "\n");
//        }
//    }

}
