package com.backend.app.controler;

import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.entity.User;
import com.backend.app.service.UserService;
import com.backend.app.service.account.AccountService;
import com.backend.app.service.admin.AdminService;
import com.backend.app.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping(value="/report/all")
    public List<User> getAllRecentUsers(){
        return adminService.getAllUsers();
    }

    @GetMapping(value = "/report/account_by_user/{id}")
    public List<Account> getAccountReport(@PathVariable long id) {
        return adminService.getAccountReportByUserId(id);
    }

    @GetMapping(value = "/report/order_by_account/{id}")
    public List<CurrencyOrder> getOrderReport(@PathVariable long id){
        return adminService.getOrderReportByAccountId(id);
    }
}
