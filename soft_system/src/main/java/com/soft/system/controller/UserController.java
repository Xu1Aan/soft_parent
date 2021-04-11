package com.soft.system.controller;

import com.soft.common.controller.BaseController;
import com.soft.common.entity.PageResult;
import com.soft.common.entity.Result;
import com.soft.common.entity.ResultCode;

import com.soft.common.poi.ExcelImportUtil;
import com.soft.common.utils.JwtUtils;
import com.soft.domain.system.Users;
import com.soft.domain.system.response.*;
import com.soft.domain.system.User;
import com.soft.system.client.DepartmentFeignClient;
import com.soft.system.service.PermissionService;
import com.soft.system.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DepartmentFeignClient departmentFeignClient;


    @RequestMapping("/user/upload/{id}")
    public Result upload(@PathVariable String id,@RequestParam(name="file") MultipartFile file ) throws IOException {
        //1.调用service保存图片（获取到图片的访问地址（dataUrl | http地址））
        String imgUrl = userService.uploadImage(id,file);
        //2.返回数据
        return new Result(ResultCode.SUCCESS,imgUrl);
    }


    /**
     * 导入Excel，添加用户
     *  文件上传：springboot
     */
    @RequestMapping(value="/user/import",method = RequestMethod.POST)
    public Result importUser(@RequestParam(name="file") MultipartFile file) throws Exception {
        //1.解析Excel
//        //1.1.根据Excel文件创建工作簿
//        Workbook wb = new XSSFWorkbook(file.getInputStream());
//        //1.2.获取Sheet
//        Sheet sheet = wb.getSheetAt(0);//参数：索引
//        //1.3.获取Sheet中的每一行，和每一个单元格
//        //2.获取用户数据列表
//        List<User> list = new ArrayList<>();
//        System.out.println(sheet.getLastRowNum());
//        for (int rowNum = 1; rowNum<= sheet.getLastRowNum() ;rowNum ++) {
//            Row row = sheet.getRow(rowNum);//根据索引获取每一个行
//            Object [] values = new Object[row.getLastCellNum()];
//            for(int cellNum=1;cellNum< row.getLastCellNum(); cellNum ++) {
//                Cell cell = row.getCell(cellNum);
//                Object value = getCellValue(cell);
//                values[cellNum] = value;
//            }
//            User user = new User(values);
//            list.add(user);
//        }

        List<User> list = new ExcelImportUtil(User.class).readExcel(file.getInputStream(), 1, 1);
        //3.批量保存用户
        userService.saveAll(list,companyId,companyName);

        return new Result(ResultCode.SUCCESS);
    }

    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellType();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                }else{
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }


    /**
     * 测试Feign组件
     * 调用系统微服务的/test接口传递部门id，通过feign调用部门微服务获取部门信息
     */
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public Result testFeign(@PathVariable(value = "id") String id) {
        Result result = departmentFeignClient.findById(id);
        return result;
    }




    /**
     * 分配角色
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String,Object> map) {
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        //1.设置保存的企业id
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        //2.调用service完成保存企业
        userService.save(user);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业id
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map map) {
        //1.获取当前的企业id

        //2.完成查询
        Page<User> pageUser = null;
        if(companyId.equals("0")){
            map.put("enbleState","1");
            pageUser = userService.findAll(map,page,size);
        }else {
            map.put("companyId",companyId);
            pageUser = userService.findAll(map, page, size);
        }
        //3.构造返回结果
        PageResult pageResult = new PageResult(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据ID查询user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        // 添加 roleIds (用户已经具有的角色id数组)
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    /**
     * 修改User
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody User user) {
        //1.设置修改的部门id
        user.setId(id);
        user.setPassword(new Md5Hash(user.getPassword(),user.getMobile(),3).toString());
        //2.调用service更新
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequiresPermissions(value = "API-USER-DELETE")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user/simple", method = RequestMethod.GET)
    public Result simple() throws Exception {
        List<UserSimpleResult> list = new ArrayList<>();
        List<User> users = userService.findAll(companyId);
        for (User user : users) {
            list.add(new UserSimpleResult(user.getId(),user.getUsername()));
        }
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 用户登录
     *  1.通过service根据mobile查询用户
     *  2.比较password
     *  3.生成jwt信息
     *
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        try {
            //1.构造登录令牌 UsernamePasswordToken
            //加密密码
            password = new Md5Hash(password,mobile,3).toString();  //1.密码，盐，加密次数
            UsernamePasswordToken upToken = new UsernamePasswordToken(mobile,password);
            //2.获取subject
            Subject subject = SecurityUtils.getSubject();
            //3.调用login方法，进入realm完成认证
            subject.login(upToken);
            //4.获取sessionId
            String sessionId = (String)subject.getSession().getId();
            //5.构造返回结果
            return new Result(ResultCode.SUCCESS,sessionId);
        }catch (Exception e) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }


//        User user = userService.findByMobile(mobile);
//        //登录失败
//        if(user == null || !user.getPassword().equals(password)) {
//            return new Result(ResultCode.MOBILEORPASSWORDERROR);
//        }else {
//            //登录成功
//            //api权限字符串
//            StringBuilder sb = new StringBuilder();
//            //获取到所有的可访问API权限
//            for (Role role : user.getRoles()) {
//                for (Permission perm : role.getPermissions()) {
//                    if(perm.getType() == PermissionConstants.PERMISSION_API) {
//                        sb.append(perm.getCode()).append(",");
//                    }
//                }
//            }
//            Map<String,Object> map = new HashMap<>();
//            map.put("apis",sb.toString());//可访问的api权限字符串
//            map.put("companyId",user.getCompanyId());
//            map.put("companyName",user.getCompanyName());
//            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
//            return new Result(ResultCode.SUCCESS,token);
//        }
    }


    /**
     * 用户登录成功之后，获取用户信息
     *      1.获取用户id
     *      2.根据用户id查询用户
     *      3.构建返回值对象
     *      4.响应
     */
    @RequestMapping(value="/profile",method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {
        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //1.subject获取所有的安全数据集合
        PrincipalCollection principals = subject.getPrincipals();
        //2.获取安全数据
        ProfileResult result = (ProfileResult)principals.getPrimaryPrincipal();

//        String userid = claims.getId();
//        //获取用户信息
//        User user = userService.findById(userid);
//        //根据不同的用户级别获取用户权限
//
//        ProfileResult result = null;
//
//        if("user".equals(user.getLevel())) {
//            result = new ProfileResult(user);
//        }else {
//            Map map = new HashMap();
//            if("coAdmin".equals(user.getLevel())) {
//                map.put("enVisible","1");
//            }
//            List<Permission> list = permissionService.findAll(map);
//            result = new ProfileResult(user,list);
//        }
        return new Result(ResultCode.SUCCESS,result);
    }

    @RequestMapping(value="/navbar",method = RequestMethod.POST)
    public Result navbar(){
        NavbarResult result = new NavbarResult();
        result.setCompanyName(this.companyName);
        result.setUserId(this.userId);
        return new Result(ResultCode.SUCCESS,result);
    }

    @RequestMapping(value="/user/updateTaste",method = RequestMethod.POST)
    public Result updateById(@RequestBody Map map){
        String id = (String) map.get("id");
        Integer taste = Integer.parseInt((String) map.get("taste"));
        if(id != null && taste != null){
            userService.updateTaste(id,taste);
            return new Result(ResultCode.SUCCESS);
        }else {
            return new Result(ResultCode.FAIL);
        }
    }

    @RequestMapping(value="/user/taste",method = RequestMethod.GET)
    public Result selectTaste(){
        List<User> users = userService.selectAllTasteByCompanyId(companyId);
        TasteResult tasteResult = new TasteResult(0,0);
        if(users != null){
            for (User user : users) {
                if(user.getTaste() > user.getDoTaste()){
                    tasteResult.setUnfinishNumber(tasteResult.getUnfinishNumber()+1);
                }else {
                    tasteResult.setFinishNumber(tasteResult.getFinishNumber()+1);
                }
            }
            return new Result(ResultCode.SUCCESS,tasteResult);
        }else {
        return new Result(ResultCode.FAIL,tasteResult);
        }
    }

    @RequestMapping(value="/user/updateSelectedTaste",method = RequestMethod.POST)
    public Result updateSelectedTaste(@RequestBody Users users){
        List<User> userList = users.getUserInfo();
        Integer taste = users.getTaste();
        if(userList != null && taste >= 0) {
            for (User user : userList) {
                String id = user.getId();
                if (id != null) {
                    userService.updateTaste(id, taste);
                } else {
                    return new Result(ResultCode.FAIL);
                }
            }
            return new Result(ResultCode.SUCCESS);
        }else{
            return new Result(ResultCode.FAIL);
        }
    }

}
