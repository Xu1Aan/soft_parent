package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.*;
import com.ihrm.domain.system.response.MyTestResult;
import com.ihrm.domain.system.response.TestResult;
import com.ihrm.domain.system.response.UserSimpleResult;
import com.ihrm.system.service.TestService;
import com.ihrm.system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.system.controller
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
        List<TestAddress> addresses = testService.findAddressById(test.getId());
        if(addresses != null) {
            testResult.setTestAddresses(addresses);
        }
        return new Result(ResultCode.SUCCESS, testResult);
    }

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

}
