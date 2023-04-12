package com.soft.system.controller;

import com.soft.common.controller.BaseController;
import com.soft.common.entity.PageResult;
import com.soft.common.entity.Result;
import com.soft.common.entity.ResultCode;
import com.soft.domain.system.*;
import com.soft.domain.system.response.ExchangeTestResult;
import com.soft.domain.system.response.TestInfoResult;
import com.soft.domain.system.response.TestResult;
import com.soft.domain.system.response.UserSimpleResult;
import com.soft.system.service.TestService;
import com.soft.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.system.controller
 * @Author: XuAn
 * @CreateTime: 2020-03-06 12:17
 * @Version: 1.0
 */
//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/sys/teacher")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;


    //保存考试设置
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result saveTest(@RequestBody TestAddressTaste testAddressTaste){
        testAddressTaste.setCompanyId(companyId);
        testService.addTest(testAddressTaste);
        return new Result(ResultCode.SUCCESS);
    }

    //查询考试
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result findAllTest(int page, int size, @RequestParam Map map){
        map.put("companyId",companyId);
        Page<Test> pageTest = testService.findAll(map,page,size);
        //构造返回结果
        PageResult pageResult = new PageResult(pageTest.getTotalElements(),pageTest.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @RequestMapping(value = "/findAllTest", method = RequestMethod.GET)
    public Result findTest(){
        List<Test> tests = testService.findByCompanyId(companyId);
        if(tests != null){
            return new Result(ResultCode.SUCCESS,tests);
        }else {
            return new Result(ResultCode.FAIL);
        }
    }

    @RequestMapping(value = "/test/myTest", method = RequestMethod.GET)
    public Result findMyTest(){
        List<Test> myTests = testService.findMyTest(userId);
        if(myTests != null) {
            return new Result(ResultCode.SUCCESS, myTests);
        } else {
            return  new Result(ResultCode.FAILTEST);
        }
    }

    //根据id查询
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id){
        Test test = testService.findById(id);
        TestResult testResult = new TestResult(test);
        String address = testService.findAddress(test.getId(),userId);
        if(address!=null) {
            testResult.setAddress(address);
        }
        List<TestAddress> addresses = testService.findAddressForSelectById(test.getId());
        if(addresses != null) {
            testResult.setTestAddresses(addresses);
        }
        return new Result(ResultCode.SUCCESS, testResult);
    }

    //根据id查询
    @RequestMapping(value = "/testinfo/{id}", method = RequestMethod.GET)
    public Result findByTestId(@PathVariable(name = "id") String id){
        TestInfoResult testInfoResult = new TestInfoResult();
        testInfoResult.setTestInfo(testService.findTestInfoByid(id));

        return new Result(ResultCode.SUCCESS, testInfoResult);

    }

    @RequiresPermissions(value = "api-test-set")
    @RequestMapping(value = "/test/setTest/{id}", method = RequestMethod.GET)
    public Result findMoreById(@PathVariable(name = "id") String id){
        Test test = testService.findById(id);
        TestResult testResult = new TestResult(test);

        List<TestAddress> addresses = testService.findAddressById(id);
        testResult.setTestAddresses(addresses);

        List<UserSimpleResult> list = new ArrayList<>();
        List<User> users = userService.findAll(companyId);
        for (User user : users) {
            list.add(new UserSimpleResult(user.getId(),user.getUsername()));
        }
        testResult.setSimpleResults(list);

        List<TestUser> testUsers = testService.findByTestId(id);
        testResult.setTestUsers(testUsers);

        return new Result(ResultCode.SUCCESS, testResult);
    }

    @RequestMapping(value = "/test/updateTest/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody TestAddressTaste testAddressTaste) {
        testAddressTaste.setId(id);
        testService.update(testAddressTaste);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/test/select", method = RequestMethod.POST)
    public Result select(@RequestBody TestUser testUser) {
        testUser.setUserId(userId);
        boolean flag = testService.selectTest(testUser);
        if(flag) {
            return new Result(ResultCode.SUCCESS);
        } else {
            return new Result(ResultCode.FAILSELECT);
        }
    }

    @RequestMapping(value = "/test/selectByAdmin", method = RequestMethod.POST)
    public Result selectByAdmin(@RequestBody TestSelectByAdmin testSelectByAdmin) {
        String testId = testSelectByAdmin.getTestId();
        List<SimpleUser> simpleUsers = testSelectByAdmin.getTableData();
        if (simpleUsers == null)
            return new Result(ResultCode.FAILSELECTBYADMIN);
        for (SimpleUser simpleUser : simpleUsers) {
            String username = simpleUser.getUsername();
            String mobile = simpleUser.getMobile();
            if(userService.existsByUsernameAndMobile(username,mobile)){
                TestUser testUser = new TestUser();
                //考试——用户中间表
                User user = userService.findByUsernameAndMobile(username,mobile);
                testUser.setUserId(user.getId());
                testUser.setTestId(testId);
                testUser.setIsTeacher(1);
                //考场安排
                TestAddress testAddress = testService.findAddressByTestId(testId);
                if (testAddress != null){
                    testUser.setAddressId(testAddress.getId());
                    testService.selectTestByAdmin(testUser);
                    //更新考试剩余人数
                } else {
                    return new Result(ResultCode.FAILSELECTBYADMIN2);
                }
            }else {
                return new Result(ResultCode.FAILSELECTBYADMIN1);
            }
        }
        return new Result(ResultCode.SUCCESS);

    }

    @RequestMapping(value = "/test/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        testService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/test/cancel/{id}", method = RequestMethod.DELETE)
    public Result cancelTest(@PathVariable(value = "id") String id){
        if(testService.testCancelById(id,userId)){
            return new Result(ResultCode.FAILCANCEL);
        }
        boolean flag = testService.cancelById(id, userId);
        if(flag){
                return new Result(ResultCode.SUCCESS);
        }else{
            return new Result(ResultCode.FAIL);
        }
    }

    @RequestMapping(value = "/test/cancelbyadmin", method = RequestMethod.DELETE)
    public Result cancelTestbyadmin(@RequestBody Map map){
        String id = (String)map.get("id");
        String userId = (String)map.get("userId");
        if(testService.testAdminCancelById(id,userId)){
            return new Result(ResultCode.FAILCANCEL);
        }
        boolean flag = testService.cancelById(id,userId);
        if(flag){
            return new Result(ResultCode.SUCCESS);
        }else{
            return new Result(ResultCode.FAIL);
        }
    }

    @RequestMapping(value = "/test/exchangeTest", method = RequestMethod.POST)
    public Result exchangeTest(@RequestBody Map map) {
        String testId = (String) map.get("id");
        String exchangeTeacherId = (String) map.get("exchangeTeacherId");
        TestUser testUser = testService.findTestUserByTestIdAndUserId(testId,userId);
        testUser.setExchangeTeacherId(exchangeTeacherId);
        if(exchangeTeacherId != null){
            boolean flag = testService.exchangeTest(testUser);
            if(flag){
                return new Result(ResultCode.SUCCESS);
            } else {
                return new Result(ResultCode.FAILEXCCHANGETEST);
            }
        }
        return new Result(ResultCode.FAIL);
    }

    @RequestMapping(value = "/test/findExchangeTest", method = RequestMethod.GET)
    public Result findExchangeTest(){
        ExchangeTestResult exchangeTestResult = testService.findExchangeTest(userId);
        if(exchangeTestResult != null){
            return new Result(ResultCode.SUCCESS,exchangeTestResult);
        }else {
            return new Result(ResultCode.FAIL);
        }

    }

    @RequestMapping(value = "/test/accessExchangeTest", method = RequestMethod.POST)
    public Result accessExchangeTest(@RequestBody Map map){
        String id = (String) map.get("id");
        boolean flag = testService.accessExchangeTest(id,userId);
        if(flag){
            return new Result(ResultCode.SUCCESS);
        }else {
            return new Result(ResultCode.FAIL);
        }
    }

}
